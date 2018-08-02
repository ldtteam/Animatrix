package com.ldtteam.animatrix;

import com.ldtteam.animatrix.handler.ClientTickEventHandler;
import com.ldtteam.animatrix.loader.animation.AnimationLoaderManager;
import com.ldtteam.animatrix.loader.animation.IAnimationLoaderManager;
import com.ldtteam.animatrix.loader.animation.collada.ColladaAnimationLoader;
import com.ldtteam.animatrix.loader.model.IModelLoaderManager;
import com.ldtteam.animatrix.loader.model.ModelLoaderManager;
import com.ldtteam.animatrix.loader.model.collada.ColladaModelLoader;
import com.ldtteam.animatrix.render.shader.AnimatrixShader;
import com.ldtteam.animatrix.util.Constants;
import com.ldtteam.animatrix.util.Log;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

@Mod(modid = Constants.General.MOD_ID, name = Constants.General.MOD_NAME, version = Constants.General.MOD_VERSION, dependencies = "required-after:graphicsexpanded")
public class ModAnimatrix
{
    // Instance of this mod use for internal and Forge references
    @Mod.Instance(Constants.General.MOD_ID)
    public static ModAnimatrix instance;

    public static ModAnimatrix getInstance()
    {
        return instance;
    }

    private AnimatrixShader shader;

    @SuppressWarnings("NewExpressionSideOnly")
    @Mod.EventHandler
    public void onFMLPreInitialization(final FMLPreInitializationEvent event)
    {
        if (event.getSide() != Side.CLIENT)
            return;

        Log.setLogger(event.getModLog());
        try
        {
            shader = new AnimatrixShader();
            IModelLoaderManager.Holder.setup(new ModelLoaderManager());
            IAnimationLoaderManager.Holder.setup(new AnimationLoaderManager());

            IModelLoaderManager.getInstance().registerLoader(new ColladaModelLoader());
            IAnimationLoaderManager.getInstance().registerLoader(new ColladaAnimationLoader());

            MinecraftForge.EVENT_BUS.register(new ClientTickEventHandler());
        }
        catch (final IOException e)
        {
            Log.getLogger().error("Failed to load Animatrix.", e);
            throw new RuntimeException("Animatrix failure during loading.");
        }
    }

    public AnimatrixShader getShader()
    {
        return shader;
    }
}
