package com.ldtteam.animatrix;

import com.ldtteam.animatrix.render.shader.AnimatrixShader;
import com.ldtteam.animatrix.util.Constants;
import com.ldtteam.animatrix.util.Log;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@Mod(modid = Constants.General.MOD_ID, name = Constants.General.MOD_NAME, version = Constants.General.MOD_VERSION)
public class AnimatrixMod
{
    // Instance of this mod use for internal and Forge references
    @Mod.Instance(Constants.General.MOD_ID)
    public static AnimatrixMod instance;

    public static AnimatrixMod getInstance()
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
        }
        catch (final IOException e)
        {
            Log.getLogger().error("Failed to load Shader.", e);
            throw new RuntimeException("Animatrix failure during loading.");
        }
    }

    public AnimatrixShader getShader()
    {
        return shader;
    }
}
