package com.ldtteam.animatrix.entity;

import com.ldtteam.animatrix.model.IModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IEntityAnimatrix
{
    /**
     * Returns the model for this entity.
     *
     * @return The model for the entity.
     */
    @OnlyIn(Dist.CLIENT)
    IModel getAnimatrixModel();
}
