package com.ldtteam.animatrix.entity;

import com.ldtteam.animatrix.model.IModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IEntityAnimatrix
{
    /**
     * Returns the model for this entity.
     *
     * @return The model for the entity.
     */
    @SideOnly(Side.CLIENT)
    IModel getAnimatrixModel();
}
