package com.ldtteam.animatrix.model.animator;

import com.ldtteam.animatrix.model.animation.IAnimation;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Consumer;

/**
 * Animators information for a animation and for example the amount of repetitions.
 */
@SideOnly(Side.CLIENT)
public class AnimatrixAnimatorAnimationInformation implements IAnimatorAnimationInformation
{
    private Double remainingCount;
    private final IAnimation animation;

    public AnimatrixAnimatorAnimationInformation(final Double remainingCount, final IAnimation animation) {
        this.remainingCount = remainingCount;
        this.animation = animation;
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
