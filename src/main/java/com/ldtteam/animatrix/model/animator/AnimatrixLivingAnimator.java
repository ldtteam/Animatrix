package com.ldtteam.animatrix.model.animator;

import com.ldtteam.animatrix.model.IModel;
import net.minecraft.entity.Entity;

public class AnimatrixLivingAnimator extends AnimatrixAnimator {

    public AnimatrixLivingAnimator(final IModel model) {
        super(model);
    }

    public void onUpdateRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {

    }

}
