package com.ldtteam.animatrix.loader.model;

import com.ldtteam.animatrix.loader.model.IModelLoader;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Signals an error during the loading of a Animatrix Model.
 */
public class ModelLoadingException extends RuntimeException
{

    public ModelLoadingException(@NotNull final Class<? extends IModelLoader> loaderClass, @NotNull final ResourceLocation location, @NotNull final Throwable cause)
    {
        super("Failed to load Model: " + location + " by: " + loaderClass.getName(), cause);
    }
}
