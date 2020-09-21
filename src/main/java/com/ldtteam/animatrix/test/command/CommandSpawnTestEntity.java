package com.ldtteam.animatrix.test.command;

import com.ldtteam.animatrix.loader.animation.IAnimationLoaderManager;
import com.ldtteam.animatrix.loader.model.IModelLoaderManager;
import com.ldtteam.animatrix.model.IModel;
import com.ldtteam.animatrix.test.entity.AnimatrixTestEntity;
import com.ldtteam.animatrix.util.Log;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

@OnlyIn(Dist.CLIENT)
public class CommandSpawnTestEntity
{

    //  /at_test animatrix:models/entities/model.dae animatrix:textures/entities/diffuse.png
    //  /at_test animatrix:models/entities/model.dae animatrix:textures/entities/diffuse.png animatrix:animations/entities/model.dae

    //  /at_test animatrix:models/entities/steve.dae minecraft:textures/entity/alex.png
    //  /at_test animatrix:models/entities/steve.dae minecraft:textures/entity/alex.png animatrix:animations/entities/steve.dae

    //  /at_test animatrix:models/entities/fr_biped.dae minecraft:textures/entity/alex.png
    //  /at_test animatrix:models/entities/fr_biped.dae minecraft:textures/entity/alex.png animatrix:animations/entities/fr_biped.dae

    //  /at_test animatrix:models/entities/human.dae animatrix:textures/entities/skin.png
    //  /at_test animatrix:models/entities/human.dae animatrix:textures/entities/skin.png animatrix:animations/entities/human.dae

    //  /at_test animatrix:models/entities/quad.dae minecraft:textures/block/blue_ice.png
    public static void register(final CommandDispatcher<CommandSource> commandSourceCommandDispatcher)
    {
        commandSourceCommandDispatcher.register(Commands.literal("at_test").then(Commands.argument("location", ResourceLocationArgument.resourceLocation()).then(Commands.argument("texture", ResourceLocationArgument.resourceLocation()).then(Commands.argument("animation", ResourceLocationArgument.resourceLocation()).executes(makeSpawnWithAnimationCommand())).executes(makeSpawnCommand()))));
    }

    public static Command<CommandSource> makeSpawnCommand() {
        return context -> {
            ServerLifecycleHooks.getCurrentServer().execute(() -> {
                final AnimatrixTestEntity entity;
                try {
                    entity = AnimatrixTestEntity.ENTITY_TYPE.create(context.getSource().asPlayer().world);
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                    return;
                }

                final ResourceLocation location = context.getArgument("location", ResourceLocation.class);
                final ResourceLocation texture = context.getArgument("texture", ResourceLocation.class);

                entity.setModel(location, texture);
                entity.setPositionAndUpdate(Minecraft.getInstance().player.getPosX(), Minecraft.getInstance().player.getPosY(), Minecraft.getInstance().player.getPosZ());

                try {
                    context.getSource().asPlayer().getServerWorld().addEntity(entity);
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                }
            });

            return 0;
        };
    }

    public static Command<CommandSource> makeSpawnWithAnimationCommand() {
        return context -> {
            Minecraft.getInstance().execute(() -> {
                final AnimatrixTestEntity entity = AnimatrixTestEntity.ENTITY_TYPE.create(Minecraft.getInstance().world);

                final ResourceLocation location = context.getArgument("location", ResourceLocation.class);
                final ResourceLocation texture = context.getArgument("texture", ResourceLocation.class);
                final ResourceLocation animation = context.getArgument("animation", ResourceLocation.class);

                entity.setModel(location, texture);
                entity.setPositionAndUpdate(Minecraft.getInstance().player.getPosX(), Minecraft.getInstance().player.getPosY(), Minecraft.getInstance().player.getPosZ());

                Minecraft.getInstance().world.addEntity(entity);
            });

            return 0;
        };
    }
}
