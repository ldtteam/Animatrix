package com.ldtteam.animatrix.entity;

import com.ldtteam.animatrix.model.IModel;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractEntityAnimatrix extends EntityLiving implements IEntityAnimatrix
{

    public AbstractEntityAnimatrix(final World worldIn)
    {
        super(worldIn);
    }

    /**
     * Gets called every tick from main Entity class
     */
    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (!world.isRemote)
        {
            //noinspection MethodCallSideOnly //Called only on client side.
            getAnimatrixModel().getAnimator().onUpdate();
        }
    }
}
