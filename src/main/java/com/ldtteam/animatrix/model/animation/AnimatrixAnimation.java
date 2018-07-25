package com.ldtteam.animatrix.model.animation;

import com.ldtteam.animatrix.model.IModel;
import com.ldtteam.animatrix.model.skeleton.IJoint;
import com.ldtteam.animatrix.util.animation.IJointTransformMath;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Matrix4f;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Represents a simple animation in Animatix.
 */
@SideOnly(Side.CLIENT)
public class AnimatrixAnimation implements IAnimation
{

    private final IModel model;

    private final int             totalLengthInTicks;
    private       int             animationTime;

    private final IKeyFrame[] keyFrames;

    public AnimatrixAnimation(final IModel model, final int totalLengthInTicks, final IKeyFrame[] keyFrames) {
        this.model = model;
        this.totalLengthInTicks = totalLengthInTicks;
        this.keyFrames = keyFrames;
    }

    /**
     * Returns to total length of the animation in ticks.
     *
     * @return The total length of the animation in ticks.
     */
    @Override
    public int getTotalLengthInTicks()
    {
        return totalLengthInTicks;
    }

    /**
     * The keyframes of this animation.
     *
     * @return The keyframes of the animation.
     */
    @Override
    public IKeyFrame[] getKeyFrames()
    {
        return keyFrames;
    }

    /**
     * This method should be called each frame to update the animation currently
     * being played. This increases the animation time (and loops it back to
     * zero if necessary), finds the pose that the entity should be in at that
     * time of the animation, and then applies that pose to all the model's
     * joints by setting the joint transforms.
     *
     * @param onAnimationCompleted Callback called when animation completes.
     */
    @Override
    public void update(final Consumer<AnimatrixAnimation> onAnimationCompleted) {
        increaseAnimationTime(onAnimationCompleted);
        final Map<String, Matrix4f> currentPose = calculateCurrentAnimationPose();
        applyPoseToJoints(currentPose, model.getSkeleton().getRootJoint(), new Matrix4f());
    }

    /**
     * Increases the current animation time which allows the animation to
     * progress. If the current animation has reached the end then the timer is
     * reset, causing the animation to loop.
     */
    private void increaseAnimationTime(final Consumer<AnimatrixAnimation> onAnimationCompleted) {
        animationTime += 1;
        if (animationTime > getTotalLengthInTicks()) {
            onAnimationCompleted.accept(this);
            this.animationTime %= getTotalLengthInTicks();
        }
    }

    /**
     * This method returns the current animation pose of the entity. It returns
     * the desired local-space transforms for all the joints in a map, indexed
     * by the name of the joint that they correspond to.
     *
     * The pose is calculated based on the previous and next keyframes in the
     * current animation. Each keyframe provides the desired pose at a certain
     * time in the animation, so the animated pose for the current time can be
     * calculated by interpolating between the previous and next keyframe.
     *
     * This method first finds the preious and next keyframe, calculates how far
     * between the two the current animation is, and then calculated the pose
     * for the current animation time by interpolating between the transforms at
     * those keyframes.
     *
     * @return The current pose as a map of the desired local-space transforms
     *         for all the joints. The transforms are indexed by the name ID of
     *         the joint that they should be applied to.
     */
    private Map<String, Matrix4f> calculateCurrentAnimationPose() {
        final IKeyFrame[] frames = getPreviousAndNextFrames();
        final float progression = calculateProgression(frames[0], frames[1]);
        return interpolatePoses(frames[0], frames[1], progression);
    }

    /**
     * This is the method where the animator calculates and sets those all-
     * important "joint transforms" that I talked about so much in the tutorial.
     *
     * This method applies the current pose to a given joint, and all of its
     * descendants. It does this by getting the desired local-transform for the
     * current joint, before applying it to the joint. Before applying the
     * transformations it needs to be converted from local-space to model-space
     * (so that they are relative to the model's origin, rather than relative to
     * the parent joint). This can be done by multiplying the local-transform of
     * the joint with the model-space transform of the parent joint.
     *
     * The same thing is then done to all the child joints.
     *
     * Finally the inverse of the joint's bind transform is multiplied with the
     * model-space transform of the joint. This basically "subtracts" the
     * joint's original bind (no animation applied) transform from the desired
     * pose transform. The result of this is then the transform required to move
     * the joint from its original model-space transform to it's desired
     * model-space posed transform. This is the transform that needs to be
     * loaded up to the vertex shader and used to transform the vertices into
     * the current pose.
     *
     * @param currentPose a map of the local-space transforms for all the joints for
     *            the desired pose. The map is indexed by the name of the joint
     *            which the transform corresponds to.
     * @param joint the current joint which the pose should be applied to.
     * @param parentTransform the desired model-space transform of the parent joint for
     *            the pose.
     */
    private void applyPoseToJoints(final Map<String, Matrix4f> currentPose, final IJoint joint, final Matrix4f parentTransform) {
        final Matrix4f currentLocalTransform = currentPose.get(joint.getName());
        final Matrix4f currentTransform = Matrix4f.mul(parentTransform, currentLocalTransform, null);
        for (final IJoint childJoint : joint.getChildJoints()) {
            applyPoseToJoints(currentPose, childJoint, currentTransform);
        }
        Matrix4f.mul(currentTransform, joint.getInverseModelSpaceBindTransform(), currentTransform);
        joint.setAnimationModelSpaceTransform(currentTransform);
    }

    /**
     * Finds the previous keyframe in the animation and the next keyframe in the
     * animation, and returns them in an array of length 2. If there is no
     * previous frame (perhaps current animation time is 0.5 and the first
     * keyframe is at time 1.5) then the first keyframe is used as both the
     * previous and next keyframe. The last keyframe is used for both next and
     * previous if there is no next keyframe.
     *
     * @return The previous and next keyframes, in an array which therefore will
     *         always have a length of 2.
     */
    private IKeyFrame[] getPreviousAndNextFrames() {
        IKeyFrame previousFrame = getKeyFrames()[0];
        IKeyFrame nextFrame = getKeyFrames()[0];
        for (int i = 1; i < getKeyFrames().length; i++) {
            nextFrame = getKeyFrames()[i];
            if (nextFrame.getTicksAfterStart() > animationTime) {
                break;
            }
            previousFrame = getKeyFrames()[i];
        }
        return new IKeyFrame[] { previousFrame, nextFrame };
    }

    /**
     * Calculates how far between the previous and next keyframe the current
     * animation time is, and returns it as a value between 0 and 1.
     *
     * @param previousFrame the previous keyframe in the animation.
     * @param nextFrame the next keyframe in the animation.
     * @return A number between 0 and 1 indicating how far between the two
     *         keyframes the current animation time is.
     */
    private float calculateProgression(final IKeyFrame previousFrame, final IKeyFrame nextFrame) {
        final float totalTime = nextFrame.getTicksAfterStart() - previousFrame.getTicksAfterStart();
        final float currentTime = animationTime - previousFrame.getTicksAfterStart();
        return currentTime / totalTime;
    }

    /**
     * Calculates all the local-space joint transforms for the desired current
     * pose by interpolating between the transforms at the previous and next
     * keyframes.
     *
     * @param previousFrame previous keyframe in the animation.
     * @param nextFrame the next keyframe in the animation.
     * @param progression a number between 0 and 1 indicating how far between the
     *            previous and next keyframes the current animation time is.
     * @return The local-space transforms for all the joints for the desired
     *         current pose. They are returned in a map, indexed by the name of
     *         the joint to which they should be applied.
     */
    private Map<String, Matrix4f> interpolatePoses(final IKeyFrame previousFrame, final IKeyFrame nextFrame, final float progression) {
        final Map<String, Matrix4f> currentPose = new HashMap<String, Matrix4f>();
        for (final String jointName : previousFrame.getJointTransformMap().keySet()) {
            final IJointTransform previousTransform = previousFrame.getJointTransformMap().get(jointName);
            final IJointTransform nextTransform = nextFrame.getJointTransformMap().get(jointName);
            final IJointTransform currentTransform = IJointTransformMath.interpolate(previousTransform, nextTransform, progression, AnimatrixJointTransform::new);
            currentPose.put(jointName, currentTransform.getJointSpaceTransformMatrix());
        }
        return currentPose;
    }

    @Override
    public String toString()
    {
        return "AnimatrixAnimation{" +
                 "totalLengthInTicks=" + totalLengthInTicks +
                 ", animationTime=" + animationTime +
                 ", keyFrames=" + Arrays.toString(keyFrames) +
                 '}';
    }
}
