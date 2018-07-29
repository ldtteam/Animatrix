package com.ldtteam.animatrix.loader.animation;

import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AnimationLoadingException extends Exception
{

    public AnimationLoadingException(@NotNull final Class<? extends IAnimationLoader> loaderClass, @NotNull final ResourceLocation location, @NotNull final Throwable cause)
    {
        super("Failed to load Animation: " + location + " by: " + loaderClass.getName(), cause);
    }
}
