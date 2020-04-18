package com.ldtteam.animatrix.render.shader;

import com.ldtteam.animatrix.util.Constants;
import com.ldtteam.graphicsexpanded.shader.program.ShaderProgram;
import com.ldtteam.graphicsexpanded.shader.uniform.UniformArray;
import com.ldtteam.graphicsexpanded.shader.uniform.UniformMatrix;
import com.ldtteam.graphicsexpanded.shader.uniform.UniformPrimitive;
import com.ldtteam.graphicsexpanded.shader.uniform.UniformVector;

import java.io.IOException;

import static com.ldtteam.animatrix.util.Constants.Shaders.ArraySizes.CONST_MAX_DIFFUSE_LIGHT_COUNT;
import static com.ldtteam.animatrix.util.Constants.Shaders.ArraySizes.CONST_MAX_JOINT_COUNT;
import static com.ldtteam.animatrix.util.Constants.Shaders.Variables.*;

public class AnimatrixShader extends ShaderProgram
{
    private final UniformMatrix.Mat4 modelViewMatrix = new UniformMatrix.Mat4("modelViewMatrix");
    private final UniformMatrix.Mat4 projectionMatrix = new UniformMatrix.Mat4("projectionMatrix");
    private final UniformArray.Mat4 jointTransforms = new UniformArray.Mat4("jointTransforms", CONST_MAX_JOINT_COUNT);

    private final UniformPrimitive.Sampler textureSampler = new UniformPrimitive.Sampler("textureSampler");
    private final UniformPrimitive.Sampler overlaySampler = new UniformPrimitive.Sampler("overlaySampler");
    private final UniformPrimitive.Sampler lightmapSampler = new UniformPrimitive.Sampler("lightmapSampler");

    private final UniformVector.Vec2 lightMapTextureCoords = new UniformVector.Vec2("lightMapTextureCoords");
    private final UniformVector.Vec2 overlayTextureCoords = new UniformVector.Vec2("overlayTextureCoords");


    public AnimatrixShader() throws IOException
    {
        super(Constants.Shaders.Vertex.ANIMATRIX_DEFAULT, Constants.Shaders.Fragment.ANIMATRIX_DEFAULT, CONST_POSITION, CONST_TEXTURE_COORDS, CONST_JOINT_INDICES, CONST_WEIGHTS);
        super.storeAllUniformLocations(
                modelViewMatrix,
                projectionMatrix,
                jointTransforms,
                textureSampler,
                overlaySampler,
                lightmapSampler,
                lightMapTextureCoords,
                overlayTextureCoords
        );
    }

    public UniformMatrix.Mat4 getModelViewMatrix() {
        return modelViewMatrix;
    }

    public UniformMatrix.Mat4 getProjectionMatrix() {
        return projectionMatrix;
    }

    public UniformArray.Mat4 getJointTransforms() {
        return jointTransforms;
    }

    public UniformVector.Vec2 getLightMapTextureCoords() {
        return lightMapTextureCoords;
    }

    public UniformVector.Vec2 getOverlayTextureCoords() {
        return overlayTextureCoords;
    }

    public UniformPrimitive.Sampler getTextureSampler() {
        return textureSampler;
    }

    public UniformPrimitive.Sampler getOverlaySampler() {
        return overlaySampler;
    }

    public UniformPrimitive.Sampler getLightmapSampler() {
        return lightmapSampler;
    }
}
