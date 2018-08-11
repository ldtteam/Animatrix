package com.ldtteam.animatrix_test.command;

import com.ldtteam.animatrix.loader.animation.AnimationLoadingException;
import com.ldtteam.animatrix.loader.animation.IAnimationLoaderManager;
import com.ldtteam.animatrix.loader.model.IModelLoaderManager;
import com.ldtteam.animatrix.model.IModel;
import com.ldtteam.animatrix.util.Log;
import com.ldtteam.animatrix_test.entity.AnimatrixTestEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CommandSpawnTestEntity extends CommandBase
{
    @Override
    public String getName()
    {
        return "at_test";
    }

    @Override
    public String getUsage(final ICommandSender sender)
    {
        return "Opens the test gui";
    }

    @Override
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args)
    {
        if (!(sender instanceof EntityPlayerMP))
        {
            return;
        }

        //  /at_test animatrix_test:models/entities/model.dae animatrix_test:textures/entities/diffuse.png
        //  /at_test animatrix_test:models/entities/model.dae animatrix_test:textures/entities/diffuse.png animatrix_test:animations/entities/model.dae

        //  /at_test animatrix_test:models/entities/steve.dae minecraft:textures/entity/alex.png
        //  /at_test animatrix_test:models/entities/steve.dae minecraft:textures/entity/alex.png animatrix_test:animations/entities/steve.dae

        //  /at_test animatrix_test:models/entities/fr_biped.dae minecraft:textures/entity/alex.png
        //  /at_test animatrix_test:models/entities/fr_biped.dae minecraft:textures/entity/alex.png animatrix_test:animations/entities/fr_biped.dae
        Minecraft.getMinecraft().addScheduledTask(() -> {
            final AnimatrixTestEntity entity = (AnimatrixTestEntity) EntityList.createEntityByIDFromName(new ResourceLocation("animatrix_test:test"), Minecraft.getMinecraft().world);

            final IModel model = IModelLoaderManager.getInstance().loadModel(new ResourceLocation(args[0]), new ResourceLocation(args[1]));
            if (args.length == 3)
            {
                try
                {
                    model.getAnimator().startAnimation(IAnimationLoaderManager.getInstance().loadAnimation(model, new ResourceLocation(args[2])));
                }
                catch (final Exception e)
                {
                    Log.getLogger().error("Failed to load animation.", e);
                }
            }

            entity.setModel(model);
            entity.setPositionAndUpdate(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ);

            Minecraft.getMinecraft().world.spawnEntity(entity);
        });
    }
}
