package com.ldtteam.animatrix_test;

import com.ldtteam.animatrix.model.IModel;
import com.ldtteam.animatrix.model.animation.IAnimation;
import com.ldtteam.animatrix.render.RenderAnimatrix;
import com.ldtteam.animatrix_test.command.CommandSpawnTestEntity;
import com.ldtteam.animatrix_test.entity.AnimatrixTestEntity;
import com.ldtteam.animatrix_test.render.AnimatrixTestRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

@Mod(modid = "animatrix_test", name = "Animatrix - Test", version = "1.0.0", dependencies = "required-after:animatrix")
public class ModAnimatrixTest
{

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void onFMLPreInitialization(final FMLPreInitializationEvent event)
    {
        if (event.getSide() != Side.CLIENT)
            return;

        MinecraftForge.EVENT_BUS.register(this);
        EntityRegistry.registerModEntity(new ResourceLocation("animatrix_test:test"), AnimatrixTestEntity.class, "animatrix_test", 0, this, Short.MAX_VALUE, 1, true);
        EntityRegistry.registerEgg(new ResourceLocation("animatrix_test:test"), Color.WHITE.getRGB(), Color.BLACK.getRGB());
        RenderingRegistry.registerEntityRenderingHandler(AnimatrixTestEntity.class, AnimatrixTestRender::new);
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void onFMLServerStarting(final FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandSpawnTestEntity());
    }
}
