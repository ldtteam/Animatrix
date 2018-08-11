package com.ldtteam.animatrix.render;

import com.ldtteam.animatrix.ModAnimatrix;
import com.ldtteam.animatrix.entity.IEntityAnimatrix;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

public class RenderAnimatrix<T extends EntityLiving & IEntityAnimatrix> extends RenderLiving<T>
{
    public RenderAnimatrix(final RenderManager rendermanagerIn, final ModelBase modelbaseIn, final float shadowsizeIn)
    {
        super(rendermanagerIn, modelbaseIn, shadowsizeIn);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(final T entity)
    {
        return entity.getAnimatrixModel().getSkin().getTexture();
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    @Override
    public void doRender(final T entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks)
    {
        if (entity.getAnimatrixModel() == null)
            return;

        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GlStateManager.disableLighting();
        bindEntityTexture(entity);

        GlStateManager.translate((float)x, (float)y, (float)z);
        //GlStateManager.rotate(90, 1f, 0, 0);
        //GlStateManager.scale(1/16f, 1/16f, 1/16f);

        ModAnimatrix.getInstance().getShader().start();

        entity.getAnimatrixModel().getSkin().getSkinModel().bind(0,1,2,3);

        ModAnimatrix.getInstance().getShader().getJointTransforms().loadMatrixArray(entity.getAnimatrixModel().getSkeleton().getAnimationModelSpaceTransformsFromJoints());
        ModAnimatrix.getInstance().getShader().getTextureSampler().loadTexUnit(OpenGlHelper.defaultTexUnit);

        GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getAnimatrixModel().getSkin().getSkinModel().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);

        entity.getAnimatrixModel().getSkin().getSkinModel().unbind(0,1,2,3);

        ModAnimatrix.getInstance().getShader().stop();
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }
}
