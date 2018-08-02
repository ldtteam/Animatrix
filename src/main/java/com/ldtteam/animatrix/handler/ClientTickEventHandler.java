package com.ldtteam.animatrix.handler;

import com.ldtteam.animatrix.entity.IEntityAnimatrix;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ClientTickEventHandler
{

    @SubscribeEvent
    public void onTickClientTick(final TickEvent.ClientTickEvent event)
    {
        if (Minecraft.getMinecraft().world == null)
            return;

        Minecraft.getMinecraft()
          .world
          .getEntities(Entity.class, IEntityAnimatrix.class::isInstance)
          .forEach(e -> ((IEntityAnimatrix)e)
                          .getAnimatrixModel()
                          .getAnimator()
                          .onUpdate()
          );
    }
}
