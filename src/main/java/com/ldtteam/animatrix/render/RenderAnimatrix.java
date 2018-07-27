package com.ldtteam.animatrix.render;

import com.ldtteam.animatrix.AnimatrixMod;
import com.ldtteam.animatrix.entity.AbstractEntityAnimatrix;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

public class RenderAnimatrix<T extends AbstractEntityAnimatrix> extends RenderLiving<T>
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
        bindEntityTexture(entity);
        AnimatrixMod.getInstance().getShader().start();
        entity.getAnimatrixModel().getSkin().getSkinModel().bind(0,1,2,3);
        AnimatrixMod.getInstance().getShader().getJointTransforms().loadMatrixArray(entity.getAnimatrixModel().getSkeleton().getAnimationModelSpaceTransformsFromJoints());
        GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getAnimatrixModel().getSkin().getSkinModel().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
        entity.getAnimatrixModel().getSkin().getSkinModel().unbind(0,1,2,3);
        AnimatrixMod.getInstance().getShader().stop();
    }
}
