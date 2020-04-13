package com.ldtteam.animatrix.handler;

import com.ldtteam.animatrix.entity.IEntityAnimatrix;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
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
}
