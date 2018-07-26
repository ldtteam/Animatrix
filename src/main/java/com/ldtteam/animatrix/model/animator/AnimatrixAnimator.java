package com.ldtteam.animatrix.model.animator;

import com.google.common.collect.Lists;
import com.ldtteam.animatrix.model.AnimatrixModel;
import com.ldtteam.animatrix.model.IModel;
import com.ldtteam.animatrix.model.animation.IAnimation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Handles the animation of {@link AnimatrixModel}
 */
@SideOnly(Side.CLIENT)
public class AnimatrixAnimator
{

    private final IModel                 model;
    private final Collection<IAnimatorAnimationInformation> runningAnimations = Lists.newArrayList();

    public AnimatrixAnimator(final IModel model) {this.model = model;}

    public void onUpdate()
    {
        final Collection<IAnimatorAnimationInformation> finishedAnimations = Lists.newArrayList();
        runningAnimations.forEach(iAnimatorAnimationInformation -> iAnimatorAnimationInformation.update(((ArrayList<IAnimatorAnimationInformation>) finishedAnimations)::add));
    }


}
