package com.ldtteam.animatrix.model.skeleton;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.util.vector.Matrix4f;

import java.util.Collection;

/**
 * Represents a single joint/joint in a Skeleton.
 */
public class AnimatrixJoint
{
    private final int index;
    private final String name;

    private final Matrix4f jointSpaceBindTransform;
    private Matrix4f       inverseModelSpaceBindTransform = new Matrix4f();

    private final ImmutableCollection<AnimatrixJoint> childJoints;

    /**
     * Creates a new joint for a {@link AnimatrixSkeleton}.
     *
     * @param index The index of the joint.
     * @param name The name of the joint.
     * @param jointSpaceBindTransform The matrix that defines the position and orientation of the joint compared to its parent.
     * @param childJoints The joints that this joint is a parent of.
     */
    public AnimatrixJoint(final int index, final String name, final Matrix4f jointSpaceBindTransform, final Collection<AnimatrixJoint> childJoints) {
        this.index = index;
        this.name = name;
        this.jointSpaceBindTransform = jointSpaceBindTransform;
        this.childJoints = ImmutableList.copyOf(childJoints);
    }

    /**
     * Returns the index of this joint in the skeleton.
     * The index is mostly used by the Shader to access the joint information in the data array.
     *
     * @return The index of the joint.
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * Returns the name of this joint.
     * The name is mostly used during animations to figure out what joints are involved in the keyframes
     * and as such in the entire animation.
     *
     * @return The name of the joint.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the joint space transformation of this joint.
     * 
     * Joint space is the space of the joint.
     * The origin of said space if the parent joint.
     * 
     * @return
     */
    public Matrix4f getJointSpaceBindTransform()
    {
        return jointSpaceBindTransform;
    }

    /**
     * Returns the joints children.
     * This joints position is the origin of the Joint space of the joints in the collection.
     *
     * @return The child joints.
     */
    public ImmutableCollection<AnimatrixJoint> getChildJoints()
    {
        return childJoints;
    }

    /**
     * Updates the {@link AnimatrixJoint#inverseModelSpaceBindTransform} form the given matrix.
     *
     * @param parentModelSpaceBindTransform
     */
    public void calculateIMSBT(@NotNull final Matrix4f parentModelSpaceBindTransform)
    {
        final Matrix4f msbt = Matrix4f.mul(parentModelSpaceBindTransform, jointSpaceBindTransform, null);
        Matrix4f.invert(msbt, inverseModelSpaceBindTransform);

        getChildJoints().forEach(joint -> joint.calculateIMSBT(msbt));
    }
}


