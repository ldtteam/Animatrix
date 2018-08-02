package com.ldtteam.animatrix_test.render;

import com.ldtteam.animatrix.render.RenderAnimatrix;
import com.ldtteam.animatrix_test.entity.AnimatrixTestEntity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;

public class AnimatrixTestRender extends RenderAnimatrix<AnimatrixTestEntity>
{
    public AnimatrixTestRender(
      final RenderManager rendermanagerIn)
    {
        super(rendermanagerIn, new ModelBiped(), 1f);
    }
}
