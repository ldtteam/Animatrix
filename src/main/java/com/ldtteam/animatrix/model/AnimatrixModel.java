package com.ldtteam.animatrix.model;

import com.ldtteam.animatrix.model.skeleton.ISkeleton;
import com.ldtteam.animatrix.model.skin.ISkin;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Represents a single model in Animatrix.
 */
@SideOnly(Side.CLIENT)
public class AnimatrixModel implements IModel
{
    private final ISkeleton skeleton;
    private final ISkin skin;

    public AnimatrixModel(final ISkeleton skeleton, final ISkin skin) {
        this.skeleton = skeleton;
        this.skin = skin;
    }

    /**
     * The skeleton of the model.
     *
     * @return The skeleton.
     */
    @Override
    public ISkeleton getSkeleton()
    {
        return skeleton;
    }

    /**
     * The skin of the model.
     *
     * @return The skin.
     */
    @Override
    public ISkin getSkin()
    {
        return skin;
    }
}
