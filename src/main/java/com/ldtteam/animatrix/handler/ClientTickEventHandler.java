package com.ldtteam.animatrix.handler;

import com.ldtteam.animatrix.entity.IEntityAnimatrix;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;


@OnlyIn(Dist.CLIENT)
public class ClientTickEventHandler
{

    @SubscribeEvent
    public void onTickClientTick(final TickEvent.ClientTickEvent event)
    {
        if (Minecraft.getInstance().world == null)
            return;

        Minecraft.getInstance()
          .world
          .getAllEntities()
          .forEach(e -> {
              if (!(e instanceof IEntityAnimatrix))
                  return;

              ((IEntityAnimatrix)e)
                  .getAnimatrixModel()
                  .getAnimator()
                  .onUpdate();
            }
          );
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void cameraSetup(EntityViewRenderEvent.CameraSetup event){
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(event.getRoll()));
        matrixStack.rotate(Vector3f.XP.rotationDegrees(event.getPitch()));
        matrixStack.rotate(Vector3f.YP.rotationDegrees(event.getYaw() + 180.0F));
        GlobalRenderHandler.getInstance().setGlobalMatrixStack(matrixStack);
    }
}
