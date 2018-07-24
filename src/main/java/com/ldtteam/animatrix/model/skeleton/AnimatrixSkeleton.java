package com.ldtteam.animatrix.model.skeleton;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AnimatrixSkeleton
{
    //The root joint of the skeleton.
    //All child joints will be stored under this joint.
    private final AnimatrixJoint rootJoint;
    //Contains the count of joints in this skeleton.
    private final int jointCount;

    public AnimatrixSkeleton(final AnimatrixJoint rootJoint) {
        this.rootJoint = rootJoint;
        this.jointCount = (int) (this.rootJoint.getChildJoints().stream().flatMap(joint -> joint.getChildJoints().stream()).count() + 1);
    }

    /**
     * Returns the root Joint for the skeleton.
     * @return The root joint for the skeleton.
     */
    public AnimatrixJoint getRootJoint()
    {
        return rootJoint;
    }

    /**
     * The total amount of joints that make up this skeleton.
     * @return The amount of joints in the skeleton.
     */
    public int getJointCount()
    {
        return jointCount;
    }
}
