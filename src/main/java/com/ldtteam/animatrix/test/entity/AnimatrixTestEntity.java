package com.ldtteam.animatrix.test.entity;

import com.google.common.collect.ImmutableList;
import com.ldtteam.animatrix.entity.IEntityAnimatrix;
import com.ldtteam.animatrix.loader.model.IModelLoaderManager;
import com.ldtteam.animatrix.model.IModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AnimatrixTestEntity extends LivingEntity implements IEntityAnimatrix
{
    private static final DataParameter<String> MODEL = EntityDataManager.createKey(AnimatrixTestEntity.class, DataSerializers.STRING);
    private static final DataParameter<String> TEXTURE = EntityDataManager.createKey(AnimatrixTestEntity.class, DataSerializers.STRING);

    public static EntityType<AnimatrixTestEntity> ENTITY_TYPE;

    private IModel model = null;

    public AnimatrixTestEntity(final World worldIn)
    {
        super(ENTITY_TYPE, worldIn);
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
        if (model == null)
            model = IModelLoaderManager.getInstance().loadModel(new ResourceLocation(getDataManager().get(MODEL)), new ResourceLocation(getDataManager().get(TEXTURE)));

        return model;
    }

    @Override
    protected void registerData() {
        super.registerData();
        getDataManager().register(MODEL, "");
        getDataManager().register(TEXTURE, "");
    }

    public void setModel(final ResourceLocation model, final ResourceLocation texture) {
        this.getDataManager().set(MODEL, model.toString());
        this.getDataManager().set(TEXTURE, texture.toString());
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
