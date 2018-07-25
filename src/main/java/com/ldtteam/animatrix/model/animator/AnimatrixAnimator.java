package com.ldtteam.animatrix.model.animator;

import com.google.common.collect.Lists;
import com.ldtteam.animatrix.model.AnimatrixModel;
import com.ldtteam.animatrix.model.IModel;
import com.ldtteam.animatrix.model.animation.IAnimation;

import java.util.Collection;

/**
 * Handles the animation of {@link AnimatrixModel}
 */
public class AnimatrixAnimator
{

    private final IModel                 model;
    private final Collection<IAnimation> runningAnimations = Lists.newArrayList();

    public AnimatrixAnimator(final IModel model) {this.model = model;}


}
