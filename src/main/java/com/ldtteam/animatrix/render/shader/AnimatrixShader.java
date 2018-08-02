package com.ldtteam.animatrix.render.shader;

import com.ldtteam.animatrix.util.Constants;
import com.ldtteam.graphicsexpanded.shader.program.ShaderProgram;
import com.ldtteam.graphicsexpanded.shader.uniform.UniformMat4Array;
import com.ldtteam.graphicsexpanded.shader.uniform.UniformSampler;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.client.renderer.GlStateManager;

import java.io.IOException;

import static com.ldtteam.animatrix.util.Constants.Shaders.ArraySizes.CONST_MAX_JOINT_COUNT;
import static com.ldtteam.animatrix.util.Constants.Shaders.Vertex.ANIMATRIX_DEFAULT;
import static com.ldtteam.animatrix.util.Constants.Shaders.Variables.*;

public class AnimatrixShader extends ShaderProgram
{
    private final UniformMat4Array jointTransforms = new UniformMat4Array("jointTransforms", CONST_MAX_JOINT_COUNT);
    private final UniformSampler textureSampler = new UniformSampler("texture");

    public AnimatrixShader() throws IOException
    {
        super(Constants.Shaders.Vertex.ANIMATRIX_DEFAULT, Constants.Shaders.Fragment.ANIMATRIX_DEFAULT, CONST_POSITION, CONST_TEXTURE_COORDS, CONST_JOINT_INDICES, CONST_WEIGHTS);
        super.storeAllUniformLocations(jointTransforms, textureSampler);
    }

    public UniformMat4Array getJointTransforms()
    {
        return jointTransforms;
    }

    public UniformSampler getTextureSampler()
    {
        return textureSampler;
    }
}
