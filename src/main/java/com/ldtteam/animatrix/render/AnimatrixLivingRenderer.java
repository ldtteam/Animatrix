package com.ldtteam.animatrix.render;

import com.ldtteam.animatrix.ModAnimatrix;
import com.ldtteam.animatrix.entity.IEntityAnimatrix;
import com.ldtteam.animatrix.model.EmptyMCModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

public class AnimatrixLivingRenderer<T extends LivingEntity & IEntityAnimatrix> extends LivingRenderer<T, EntityModel<T>>
{
    public AnimatrixLivingRenderer(final EntityRendererManager entityRendererManager, final float shadowSize)
    {
        super(entityRendererManager, new EmptyMCModel<>(), shadowSize);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @NotNull
    @Override
    public ResourceLocation getEntityTexture(final T entity)
    {
        if (entity.getAnimatrixModel() == null)
            return new ResourceLocation("missingno");

        return entity.getAnimatrixModel().getSkin().getTexture();
    }

    @Nullable
    protected RenderType getRenderType(T entityType, boolean isVisible, boolean visibleToPlayer) {
        ResourceLocation resourcelocation = this.getEntityTexture(entityType);
        if (visibleToPlayer) {
            return RenderType.getEntityTranslucent(resourcelocation);
        } else if (isVisible) {
            return RenderType.getEntityTranslucent(resourcelocation);
        } else {
            return entityType.isGlowing() ? RenderType.getOutline(resourcelocation) : null;
        }
    }

    protected boolean isVisible(T livingEntityIn) {
        return !livingEntityIn.isInvisible();
    }

    @Override
    public void render(@NotNull final T entityIn, final float entityYaw, final float partialTicks, @NotNull final MatrixStack matrixStackIn, @NotNull final IRenderTypeBuffer bufferIn, final int packedLightIn) {
        boolean visible = this.isVisible(entityIn);
        boolean visibleToPlayer = !visible && !entityIn.isInvisibleToPlayer(Objects.requireNonNull(Minecraft.getInstance().player));
        RenderType rendertype = this.getRenderType(entityIn, visible, visibleToPlayer);
        GameRenderer gameRenderer = Minecraft.getInstance().gameRenderer;
        Matrix4f projMatrix = gameRenderer.getProjectionMatrix(gameRenderer.getActiveRenderInfo(), partialTicks, true);
        int packedOverlay = LivingRenderer.getPackedOverlay(entityIn, partialTicks);

        drawModel(rendertype, entityIn,
          matrixStackIn,
                projMatrix, packedLightIn,
                packedOverlay);
    }

    public void drawModel(
      RenderType renderType, T entityIn,
      MatrixStack matrixStackIn, Matrix4f projectionMatrix, int packedLightIn,
      int packedOverlay){

        if (entityIn.getAnimatrixModel() == null)
            return;

        matrixStackIn.push();

        //Update all the poses, so that the accurate partial tick time is reflected.
        entityIn.getAnimatrixModel().getAnimator().onPreRender();

        //Setup the shader.
        ModAnimatrix.getInstance().getShader().start();
        renderType.setupRenderState();
        ModAnimatrix.getInstance().getShader().getModelViewMatrix().load(matrixStackIn.getLast().getMatrix());
        ModAnimatrix.getInstance().getShader().getProjectionMatrix().load(projectionMatrix);
        ModAnimatrix.getInstance().getShader().getLightMapTextureCoords().load(((packedLightIn & 0xFFFF) / 256.0f) + 0.03125f, ((packedLightIn >>> 16) / 256.0f) + 0.03125f);
        ModAnimatrix.getInstance().getShader().getOverlayTextureCoords().load((short)(packedOverlay & '\uffff') / 16.0f, (short)(packedOverlay >>> 16 & '\uffff') / 16.0f);

        ModAnimatrix.getInstance().getShader().getJointTransforms().load(entityIn.getAnimatrixModel().getSkeleton().getAnimationModelSpaceTransformsFromJoints());

        entityIn.getAnimatrixModel().getSkin().getSkinModel().bind(0,1,2,3,4);

        GL11.glDrawElements(GL11.GL_TRIANGLES, entityIn.getAnimatrixModel().getSkin().getSkinModel().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);

        entityIn.getAnimatrixModel().getSkin().getSkinModel().unbind(0,1,2,3,4);

        renderType.clearRenderState();
        ModAnimatrix.getInstance().getShader().stop();
        matrixStackIn.pop();
    }
}
