package com.ldtteam.animatrix.loader.animation.collada;

import com.ldtteam.animatrix.loader.animation.AnimationLoadingException;
import com.ldtteam.animatrix.loader.data.AnimationData;
import com.ldtteam.animatrix.util.xml.XmlNode;
import com.ldtteam.animatrix.util.xml.XmlParser;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ColladaAnimationLoader implements com.ldtteam.animatrix.loader.animation.IAnimationLoader
{

    @Override
    public boolean canLoadAnimation(final ResourceLocation animationLocation)
    {
        return animationLocation.getPath().endsWith(".dae");
    }

    @Override
    public AnimationData loadAnimation(final ResourceLocation animationLocation) throws AnimationLoadingException {
        try
        {
            final XmlNode node = XmlParser.loadXmlFile(animationLocation);
            final XmlNode animNode = node.getChild("library_animations");
            final XmlNode jointsNode = node.getChild("library_visual_scenes");
            final ColladaAnimationExtractor loader = new ColladaAnimationExtractor(animNode, jointsNode);
            final AnimationData animData = loader.extractAnimation();
            return animData;
        }
        catch (final Exception e)
        {
            throw new AnimationLoadingException(this.getClass(), animationLocation, e);
        }

    }

}
