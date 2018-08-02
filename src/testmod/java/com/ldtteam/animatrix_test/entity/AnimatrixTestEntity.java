package com.ldtteam.animatrix_test.entity;

import com.ldtteam.animatrix.entity.IEntityAnimatrix;
import com.ldtteam.animatrix.model.IModel;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AnimatrixTestEntity extends EntityLiving implements IEntityAnimatrix
{

    private IModel model;

    public AnimatrixTestEntity(final World worldIn)
    {
        super(worldIn);
    }

    public void setModel(final IModel model)
    {
        this.model = model;
    }

    /**
     * Returns the model for this entity.
     *
     * @return The model for the entity.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public IModel getAnimatrixModel()
    {
        return model;
    }
}
