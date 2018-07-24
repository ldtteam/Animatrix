package com.ldtteam.animatrix.model.skeleton;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.util.vector.Matrix4f;

import java.util.Collection;

/**
 * Represents a single joint/bone in a Skeleton.
 */
public class AnimatrixJoint
{
    private final int index;
    private final String name;

    private final Matrix4f boneSpaceBindTransform;
    private Matrix4f       inverseModelSpaceBindTransform = new Matrix4f();

    private final Collection<AnimatrixJoint> childJoints;

    public AnimatrixJoint(final int index, final String name, final Matrix4f boneSpaceBindTransform, final Collection<AnimatrixJoint> childJoints) {
        this.index = index;
        this.name = name;
        this.boneSpaceBindTransform = boneSpaceBindTransform;
        this.childJoints = ImmutableList.copyOf(childJoints);
    }

    public int getIndex()
    {
        return index;
    }

    public String getName()
    {
        return name;
    }

    public Matrix4f getBoneSpaceBindTransform()
    {
        return boneSpaceBindTransform;
    }

    public Collection<AnimatrixJoint> getChildJoints()
    {
        return childJoints;
    }

    public void calculateIMSBT(@NotNull final Matrix4f parentModelSpaceBindTransform)
    {
        final Matrix4f msbt = Matrix4f.mul(parentModelSpaceBindTransform, boneSpaceBindTransform, null);
        Matrix4f.invert(msbt, inverseModelSpaceBindTransform);

        getChildJoints().forEach(joint -> joint.calculateIMSBT(msbt));
    }
}


