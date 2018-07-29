package com.ldtteam.animatrix.loader.model;

import com.ldtteam.animatrix.loader.data.AnimatedModelData;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public interface IModelLoader
{
    boolean canLoadModel(@NotNull ResourceLocation modelLocation);

    AnimatedModelData loadModel(ResourceLocation colladaFile) throws ModelLoadingException;
}
