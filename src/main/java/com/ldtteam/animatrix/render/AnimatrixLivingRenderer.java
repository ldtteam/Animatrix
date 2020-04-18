package com.ldtteam.animatrix.render;

import com.ldtteam.animatrix.ModAnimatrix;
import com.ldtteam.animatrix.entity.IEntityAnimatrix;
import com.ldtteam.animatrix.model.EmptyMCModel;
import com.ldtteam.graphicsexpanded.util.math.Vector2f;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

import static org.lwjgl.opengl.GL13.*;

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
            return RenderType.getEntityCutoutNoCull(resourcelocation);
        } else {
            return entityType.isGlowing() ? RenderType.getOutline(resourcelocation) : null;
        }
    }

    protected boolean isVisible(T livingEntityIn) {
        return !livingEntityIn.isInvisible();
    }

    @Override
    public void render(final T entityIn, final float entityYaw, final float partialTicks, final MatrixStack matrixStackIn, final IRenderTypeBuffer bufferIn, final int packedLightIn) {
        boolean visible = this.isVisible(entityIn);
        boolean visibleToPlayer = !visible && !entityIn.isInvisibleToPlayer(Objects.requireNonNull(Minecraft.getInstance().player));
        RenderType rendertype = this.getRenderType(entityIn, visible, visibleToPlayer);
        GameRenderer gameRenderer = Minecraft.getInstance().gameRenderer;
        net.minecraft.client.renderer.Matrix4f projMatrix = gameRenderer.getProjectionMatrix(gameRenderer.getActiveRenderInfo(), partialTicks, true);
        int packedOverlay = LivingRenderer.getPackedOverlay(entityIn, partialTicks);

        drawModel(rendertype, entityIn,
                entityYaw, partialTicks, matrixStackIn,
                projMatrix, packedLightIn,
                packedOverlay, bufferIn);
    }

    public void drawModel(RenderType renderType, T entityIn, float entityYaw, float partialTicks,
                          MatrixStack matrixStackIn, Matrix4f projectionMatrix, int packedLightIn,
                          int packedOverlay, IRenderTypeBuffer buffer){

        if (entityIn.getAnimatrixModel() == null)
            return;

        matrixStackIn.push();
        //matrixStackIn.translate(entityIn.getPosX(), entityIn.getPosY(), entityIn.getPosZ());
        //matrixStackIn.rotate(new Quaternion(Vector3f.YP, entityYaw, false));

        matrixStackIn.scale(16f, 16f, 16f);

        //Setup the shader.
        ModAnimatrix.getInstance().getShader().start();
        renderType.setupRenderState();
        RenderSystem.disableCull();
        RenderSystem.disableLighting();
        RenderSystem.disableDepthTest();
        Minecraft.getInstance().getTextureManager().bindTexture(getEntityTexture(entityIn));
        ModAnimatrix.getInstance().getShader().getModelViewMatrix().load(matrixStackIn.getLast().getMatrix());
        ModAnimatrix.getInstance().getShader().getProjectionMatrix().load(projectionMatrix);
        ModAnimatrix.getInstance().getShader().getLightMapTextureCoords().load(((packedLightIn & 0xFFFF) / 256.0f) + 0.03125f, ((packedLightIn >>> 16) / 256.0f) + 0.03125f);
        ModAnimatrix.getInstance().getShader().getOverlayTextureCoords().load((short)(packedOverlay & '\uffff') / 16.0f, (short)(packedOverlay >>> 16 & '\uffff') / 16.0f);
        ModAnimatrix.getInstance().getShader().getJointTransforms().load(entityIn.getAnimatrixModel().getSkeleton().getAnimationModelSpaceTransformsFromJoints());

        ModAnimatrix.getInstance().getShader().getTextureSampler().load(0);
        //ModAnimatrix.getInstance().getShader().getTextureSampler().load(1);
        //ModAnimatrix.getInstance().getShader().getTextureSampler().load(2);

        entityIn.getAnimatrixModel().getSkin().getSkinModel().bind(0,1,2,3,4);

        GL11.glDrawElements(GL11.GL_TRIANGLES, entityIn.getAnimatrixModel().getSkin().getSkinModel().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);

        entityIn.getAnimatrixModel().getSkin().getSkinModel().unbind(0,1,2,3,4);

        ModAnimatrix.getInstance().getShader().stop();
        renderType.clearRenderState();
        matrixStackIn.pop();
    }
}
