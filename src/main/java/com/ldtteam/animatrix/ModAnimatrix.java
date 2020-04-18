package com.ldtteam.animatrix;

import com.ldtteam.animatrix.handler.ClientTickEventHandler;
import com.ldtteam.animatrix.loader.animation.AnimationLoaderManager;
import com.ldtteam.animatrix.loader.animation.IAnimationLoaderManager;
import com.ldtteam.animatrix.loader.animation.collada.ColladaAnimationLoader;
import com.ldtteam.animatrix.loader.model.IModelLoaderManager;
import com.ldtteam.animatrix.loader.model.ModelLoaderManager;
import com.ldtteam.animatrix.loader.model.collada.ColladaModelLoader;
import com.ldtteam.animatrix.render.shader.AnimatrixShader;
import com.ldtteam.animatrix.test.command.CommandSpawnTestEntity;
import com.ldtteam.animatrix.test.entity.AnimatrixTestEntity;
import com.ldtteam.animatrix.test.render.AnimatrixTestRender;
import com.ldtteam.animatrix.util.Constants;
import com.ldtteam.animatrix.util.Log;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

@Mod(Constants.General.MOD_ID)
public class ModAnimatrix
{
    // Instance of this mod use for internal and Forge references
    public static ModAnimatrix instance;

    public static ModAnimatrix getInstance()
    {
        return instance;
    }

    private AnimatrixShader shader;

    public ModAnimatrix() {
        instance = this;
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> Mod.EventBusSubscriber.Bus.MOD.bus().get().addGenericListener(EntityType.class, this::registerEntity));
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> Mod.EventBusSubscriber.Bus.FORGE.bus().get().addListener(this::onServerStarting));
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> Mod.EventBusSubscriber.Bus.MOD.bus().get().addListener(this::onClientInitialization));
    }

    private void registerEntity(final RegistryEvent.Register<EntityType<?>> entityTypeRegister)
    {
        AnimatrixTestEntity.ENTITY_TYPE = EntityType.Builder.create((EntityType.IFactory<AnimatrixTestEntity>) (p_create_1_, p_create_2_) -> new AnimatrixTestEntity(p_create_2_), EntityClassification.MISC)
                .build("animatrix:test");
        AnimatrixTestEntity.ENTITY_TYPE.setRegistryName("animatrix:test");

        entityTypeRegister.getRegistry().register(AnimatrixTestEntity.ENTITY_TYPE);
    }

    private void onServerStarting(final FMLServerStartingEvent serverStartingEvent)
    {
        CommandSpawnTestEntity.register(serverStartingEvent.getCommandDispatcher());
    }

    private void onClientInitialization(final FMLClientSetupEvent event)
    {
        Log.setLogger(LogManager.getLogger());
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            DeferredWorkQueue.runLaterChecked(() -> {
                try
                {
                    shader = new AnimatrixShader();
                    IModelLoaderManager.Holder.setup(new ModelLoaderManager());
                    IAnimationLoaderManager.Holder.setup(new AnimationLoaderManager());

                    IModelLoaderManager.getInstance().registerLoader(new ColladaModelLoader());
                    IAnimationLoaderManager.getInstance().registerLoader(new ColladaAnimationLoader());
                }
                catch (final IOException e)
                {
                    Log.getLogger().error("Failed to load Animatrix.", e);
                    throw new RuntimeException("Animatrix failure during loading.");
                }
            });

            MinecraftForge.EVENT_BUS.register(new ClientTickEventHandler());
            Minecraft.getInstance().getRenderManager().register(AnimatrixTestEntity.ENTITY_TYPE, new AnimatrixTestRender(Minecraft.getInstance().getRenderManager()));
        });
    }

    public AnimatrixShader getShader()
    {
        return shader;
    }
}
