package com.ldtteam.animatrix.test.render;

import com.ldtteam.animatrix.render.AnimatrixLivingRenderer;
import com.ldtteam.animatrix.test.entity.AnimatrixTestEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class AnimatrixTestRender extends AnimatrixLivingRenderer<AnimatrixTestEntity>
{
    public AnimatrixTestRender(final EntityRendererManager entityRendererManager) {
        super(entityRendererManager, 1f);
    }
}
