package com.ldtteam.animatrix.test.render;

import com.ldtteam.animatrix.render.RenderAnimatrix;
import com.ldtteam.animatrix.test.entity.AnimatrixTestEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class AnimatrixTestRender extends RenderAnimatrix<AnimatrixTestEntity>
{
    public AnimatrixTestRender(final EntityRendererManager entityRendererManager) {
        super(entityRendererManager, 1f);
    }
}
