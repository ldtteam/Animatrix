package com.ldtteam.animatrix.model.animation;

import com.ldtteam.animatrix.model.skeleton.IJoint;
import com.ldtteam.graphicsexpanded.util.math.Matrix4f;
import com.ldtteam.graphicsexpanded.util.math.Quaternion;
import com.ldtteam.graphicsexpanded.util.math.Vector3f;

/**
 * Represents a single transform of a single joint in a keyframe.
 */
public interface IJointTransform
{
    /**
     * The name of the {@link IJoint} that is being transformed by this.
     *
     * @return The name of the {@link IJoint}.
     */
    String getJointName();

    /**
     * The position that the {@link IJoint} needs to be in.
     *
     * @return The position of the {@link IJoint}.
     */
    Vector3f getPosition();

    /**
     * The rotation that the {@link IJoint} needs to have.
     *
     * @return The rotation of the {@link IJoint}.
     */
    Quaternion getRotation();

    /**
     * Returns the transforming matrix in joint space for this.
     *
     * @return The transforming matrix.
     */
    Matrix4f getJointSpaceTransformMatrix();
}
