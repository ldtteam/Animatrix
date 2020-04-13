package com.ldtteam.animatrix.render;

import com.ldtteam.animatrix.ModAnimatrix;
import com.ldtteam.animatrix.entity.IEntityAnimatrix;
import com.ldtteam.graphicsexpanded.util.math.Matrix4f;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.nio.FloatBuffer;

public class RenderAnimatrix<T extends LivingEntity & IEntityAnimatrix> extends LivingRenderer<T, EntityModel<T>>
{
    public RenderAnimatrix(final EntityRendererManager entityRendererManager, final float shadowSize)
    {
        super(entityRendererManager, new BipedModel<T>(1f), shadowSize);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @NotNull
    @Override
    public ResourceLocation getEntityTexture(final T entity)
    {
        return entity.getAnimatrixModel().getSkin().getTexture();
    }

    @Override
    public void render(final T entityIn, final float entityYaw, final float partialTicks, final MatrixStack matrixStackIn, final IRenderTypeBuffer bufferIn, final int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (entityIn.getAnimatrixModel() == null)
            return;

        RenderSystem.pushMatrix();
        RenderSystem.disableCull();
        RenderSystem.disableLighting();
        Minecraft.getInstance().getTextureManager().bindTexture(getEntityTexture(entityIn));

        RenderSystem.translated(entityIn.getPosX(), entityIn.getPosY(), entityIn.getPosZ());

        ModAnimatrix.getInstance().getShader().start();

        entityIn.getAnimatrixModel().getSkin().getSkinModel().bind(0,1,2,3);

        RenderSystem.matrixMode(5889);
        RenderSystem.loadIdentity();
        RenderSystem.multMatrix(matri);
        RenderSystem.matrixMode(5888);

        //ModAnimatrix.getInstance().getShader().getWorldTranslationMatrix().loadMatrix(new Matrix4f());
        ModAnimatrix.getInstance().getShader().getJointTransforms().loadMatrixArray(entityIn.getAnimatrixModel().getSkeleton().getAnimationModelSpaceTransformsFromJoints());
        final Texture texture = Minecraft.getInstance().getTextureManager().getTexture(getEntityTexture(entityIn));
        ModAnimatrix.getInstance().getShader().getTextureSampler().loadTexUnit(texture.getGlTextureId());

        GL11.glDrawElements(GL11.GL_TRIANGLES, entityIn.getAnimatrixModel().getSkin().getSkinModel().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);

        entityIn.getAnimatrixModel().getSkin().getSkinModel().unbind(0,1,2,3);

        ModAnimatrix.getInstance().getShader().stop();
        RenderSystem.enableCull();
        RenderSystem.popMatrix();
    }

    private Matrix4f toGEMatrix(net.minecraft.client.renderer.Matrix4f mcMatrix)
    {
        final Matrix4f matrix4f = new Matrix4f();
        final FloatBuffer buffer = FloatBuffer.allocate(16);
        mcMatrix.write(buffer);
        matrix4f.load(buffer);
        buffer.clear();
        return matrix4f;
    }
}
