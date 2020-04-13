package com.ldtteam.animatrix.model.animator;

import com.ldtteam.animatrix.model.animation.IAnimation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Consumer;

/**
 * Animators information for a animation and for example the amount of repetitions.
 */
@OnlyIn(Dist.CLIENT)
public class AnimatrixAnimatorAnimationInformation implements IAnimatorAnimationInformation
{
    private final IAnimation animation;
    private Double remainingCount;

    public AnimatrixAnimatorAnimationInformation( final IAnimation animation, final Double remainingCount) {
        this.animation = animation;
        this.remainingCount = remainingCount;
    }

    /**
     * Call this once per tick to update the animation.
     *
     * @param onAnimationRepetitionEnded Callback called when the animation has ended.
     */
    @Override
    public void update(final Consumer<IAnimatorAnimationInformation> onAnimationRepetitionEnded)
    {
        getAnimation().update(finishedAnimation -> remainingCount--);
        if (getRemainingCount() == 0)
        {
            onAnimationRepetitionEnded.accept(this);
        }
    }

    /**
     * Returns the remaining count that the animations still needs to be repeated.
     * @return The
     */
    @Override
    public Double getRemainingCount()
    {
        return remainingCount;
    }

    /**
     * The animation.
     * @return The animation.
     */
    @Override
    public IAnimation getAnimation()
    {
        return animation;
    }

    @Override
    public String toString()
    {
        return "AnimatrixAnimatorAnimationInformation{" +
                 "remainingCount=" + remainingCount +
                 ", animation=" + animation +
                 '}';
    }
}
