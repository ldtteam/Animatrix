package com.ldtteam.animatrix.test.entity;

import com.google.common.collect.ImmutableList;
import com.ldtteam.animatrix.entity.IEntityAnimatrix;
import com.ldtteam.animatrix.model.IModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AnimatrixTestEntity extends LivingEntity implements IEntityAnimatrix
{

    public static EntityType<AnimatrixTestEntity> ENTITY_TYPE;

    private static IModel model;

    public AnimatrixTestEntity(final World worldIn)
    {
        super(ENTITY_TYPE, worldIn);
    }

    public void setModel(final IModel model)
    {
        AnimatrixTestEntity.model = model;
    }

    /**
     * Returns the model for this entity.
     *
     * @return The model for the entity.
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public IModel getAnimatrixModel()
    {
        return AnimatrixTestEntity.model;
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return ImmutableList.of();
    }

    @Override
    public ItemStack getItemStackFromSlot(final EquipmentSlotType slotIn) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(final EquipmentSlotType slotIn, final ItemStack stack) {
        //NOOP
    }

    @Override
    public HandSide getPrimaryHand() {
        return HandSide.RIGHT;
    }


}
