package com.ldtteam.animatrix.model.animation;

import java.util.function.Consumer;

public interface IAnimation
{
    /**
     * Returns to total length of the animation in ticks.
     *
     * @return The total length of the animation in ticks.
     */
    int getTotalLengthInTicks();

    /**
     * The keyframes of this animation.
     *
     * @return The keyframes of the animation.
     */
    IKeyFrame[] getKeyFrames();

    /**
     * This method should be called each frame to update the animation currently
     * being played. This increases the animation time (and loops it back to
     * zero if necessary), finds the pose that the entity should be in at that
     * time of the animation, and then applies that pose to all the model's
     * joints by setting the joint transforms.
     *
     * @param onAnimationCompleted Callback called when animation completes.
     */
    void update(Consumer<IAnimation> onAnimationCompleted);
}
