package com.ldtteam.animatrix.model.animation;

import com.ldtteam.animatrix.model.skeleton.IJoint;
import com.ldtteam.graphicsexpanded.util.math.Matrix4f;
import com.ldtteam.graphicsexpanded.util.math.Quaternion;
import com.ldtteam.graphicsexpanded.util.math.QuaternionMath;
import com.ldtteam.graphicsexpanded.util.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Represents a single transform of a single {@link IJoint} in a {@link IKeyFrame}
 */
@OnlyIn(Dist.CLIENT)
public class AnimatrixJointTransform implements IJointTransform
{

    private final String jointName;
    private final Vector3f position;
    private final Quaternion rotation;

    public AnimatrixJointTransform(final String jointName, final Vector3f position, final Quaternion rotation) {
        this.jointName = jointName;
        this.position = position;
        this.rotation = rotation;
    }

    /**
     * The name of the {@link IJoint} that is being transformed by this.
     *
     * @return The name of the {@link IJoint}.
     */
    @Override
    public String getJointName()
    {
        return jointName;
    }

    /**
     * The position that the {@link IJoint} needs to be in.
     *
     * @return The position of the {@link IJoint}.
     */
    @Override
    public Vector3f getPosition()
    {
        return position;
    }

    /**
     * The rotation that the {@link IJoint} needs to have.
     *
     * @return The rotation of the {@link IJoint}.
     */
    @Override
    public Quaternion getRotation()
    {
        return rotation;
    }

    /**
     * Returns the transforming matrix in joint space for this.
     *
     * @return The transforming matrix.
     */
    @Override
    public Matrix4f getJointSpaceTransformMatrix()
    {
        final Matrix4f matrix4f = new Matrix4f();
        matrix4f.translate(position);
        Matrix4f.mul(matrix4f, QuaternionMath.toRotationMatrix(rotation), matrix4f);
        return matrix4f;
    }

    @Override
    public String toString()
    {
        return "AnimatrixJointTransform{" +
                 "jointName='" + jointName + '\'' +
                 ", position=" + position +
                 ", rotation=" + rotation +
                 '}';
    }
}
