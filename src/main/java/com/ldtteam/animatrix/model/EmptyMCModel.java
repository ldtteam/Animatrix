package com.ldtteam.animatrix.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;

public class EmptyMCModel<T extends Entity> extends EntityModel<T> {

    public EmptyMCModel()
    {
    }

    @Override
    public void render(
                    final MatrixStack matrixStackIn,
                    final IVertexBuilder bufferIn,
                    final int packedLightIn,
                    final int packedOverlayIn,
                    final float red,
                    final float green,
                    final float blue,
                    final float alpha) {

    }

    /**
     * Sets this entity's model rotation angles
     *
     * @param entityIn
     * @param limbSwing
     * @param limbSwingAmount
     * @param ageInTicks
     * @param netHeadYaw
     * @param headPitch
     */
    @Override
    public void setRotationAngles(final T entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch)
    {

    }
}
