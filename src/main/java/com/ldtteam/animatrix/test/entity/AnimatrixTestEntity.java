package com.ldtteam.animatrix.test.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ldtteam.animatrix.entity.IEntityAnimatrix;
import com.ldtteam.animatrix.loader.animation.AnimationLoaderManager;
import com.ldtteam.animatrix.loader.animation.AnimationLoadingException;
import com.ldtteam.animatrix.loader.animation.IAnimationLoaderManager;
import com.ldtteam.animatrix.loader.model.IModelLoaderManager;
import com.ldtteam.animatrix.model.IModel;
import com.ldtteam.animatrix.model.animation.IAnimation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class AnimatrixTestEntity extends LivingEntity implements IEntityAnimatrix
{
    private static final DataParameter<String> MODEL = EntityDataManager.createKey(AnimatrixTestEntity.class, DataSerializers.STRING);
    private static final DataParameter<String> TEXTURE = EntityDataManager.createKey(AnimatrixTestEntity.class, DataSerializers.STRING);
    private static final DataParameter<String> QUEUED_ANIMATIONS = EntityDataManager.createKey(AnimatrixTestEntity.class, DataSerializers.STRING);

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
        {
            model = IModelLoaderManager.getInstance().loadModel(new ResourceLocation(getDataManager().get(MODEL)), new ResourceLocation(getDataManager().get(TEXTURE)));
            final String queuedAnimation = getDataManager().get(QUEUED_ANIMATIONS);
            final ResourceLocation queuedAnimationId = new ResourceLocation(queuedAnimation);

            try
            {
                final IAnimation animation = IAnimationLoaderManager.getInstance().loadAnimation(model, queuedAnimationId);
                model.getAnimator().startAnimation(animation);
            }
            catch (AnimationLoadingException e)
            {
                e.printStackTrace();
            }
        }

        return model;
    }

    @Override
    protected void registerData() {
        super.registerData();
        getDataManager().register(MODEL, "");
        getDataManager().register(TEXTURE, "");
        getDataManager().register(QUEUED_ANIMATIONS, "");
    }

    public void setModel(final ResourceLocation model, final ResourceLocation texture) {
        this.getDataManager().set(MODEL, model.toString());
        this.getDataManager().set(TEXTURE, texture.toString());
    }

    public void queueAnimation(final ResourceLocation animation) {
        this.getDataManager().set(QUEUED_ANIMATIONS, animation.toString());
    }

    @NotNull
    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return ImmutableList.of();
    }

    @NotNull
    @Override
    public ItemStack getItemStackFromSlot(@NotNull final EquipmentSlotType slotIn) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(@NotNull final EquipmentSlotType slotIn, @NotNull final ItemStack stack) {
        //NOOP
    }

    @NotNull
    @Override
    public HandSide getPrimaryHand() {
        return HandSide.RIGHT;
    }


}
