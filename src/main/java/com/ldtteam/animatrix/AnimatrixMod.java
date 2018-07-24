package com.ldtteam.animatrix;

import com.ldtteam.animatrix.util.Constants;
import com.ldtteam.animatrix.util.Log;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.General.MOD_ID, name = Constants.General.MOD_NAME, version = Constants.General.MOD_VERSION)
public class AnimatrixMod
{

    @Mod.EventHandler
    public void onFMLPreInitialization(final FMLPreInitializationEvent event)
    {
        Log.setLogger(event.getModLog());
    }
}
