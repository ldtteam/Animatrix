package com.ldtteam.animatrix.render.shader;

import com.ldtteam.graphicsexpanded.shader.program.ShaderProgram;
import com.ldtteam.graphicsexpanded.shader.uniform.UniformMat4Array;

import java.io.IOException;

import static com.ldtteam.animatrix.util.Constants.Shaders.ArraySizes.CONST_MAX_JOINT_COUNT;
import static com.ldtteam.animatrix.util.Constants.Shaders.Vertex.ANIMATRIX_DEFAULT;
import static com.ldtteam.animatrix.util.Constants.Shaders.Variables.*;

public class AnimatrixShader extends ShaderProgram
{
    private final UniformMat4Array jointTransforms = new UniformMat4Array("jointTransforms", CONST_MAX_JOINT_COUNT);

    public AnimatrixShader() throws IOException
    {
        super(ANIMATRIX_DEFAULT, null, CONST_POSITION, CONST_TEXTURE_COORDS, CONST_JOINT_INDICES, CONST_WEIGHTS);
        super.storeAllUniformLocations(jointTransforms);
    }

    public UniformMat4Array getJointTransforms()
    {
        return jointTransforms;
    }
}
