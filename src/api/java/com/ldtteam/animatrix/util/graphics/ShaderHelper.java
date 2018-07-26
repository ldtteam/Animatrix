package com.ldtteam.animatrix.util.graphics;

import com.ldtteam.animatrix.util.Constants;
import com.ldtteam.animatrix.util.Log;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;

import java.io.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SideOnly(Side.CLIENT)
public final class ShaderHelper {

    private static final int VERT = ARBVertexShader.GL_VERTEX_SHADER_ARB;
    private static final int FRAG = ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;

    public static int shader_animatrix_entity;

    private static boolean lighting;

    private static void deleteShader(final int id) {
        if (id != 0) {
            ARBShaderObjects.glDeleteObjectARB(id);
        }
    }

    public static void initShaders() {
        if (Minecraft.getMinecraft().getResourceManager() instanceof SimpleReloadableResourceManager) {
            ((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(manager -> {
                deleteShader(shader_animatrix_entity); shader_animatrix_entity = 0;

                loadShaders();
            });
        }
    }

    private static void loadShaders() {
        shader_animatrix_entity = createProgram(Constants.Shaders.Vertex.ANIMATRIX_DEFAULT, null);
    }

    public static void useShader(final int shader, final Consumer<Integer> callback) {
        lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
        GlStateManager.disableLighting();

        ARBShaderObjects.glUseProgramObjectARB(shader);

        if(shader != 0) {
            if(callback != null)
                callback.accept(shader);
        }
    }

    public static void useShader(final int shader) {
        useShader(shader, null);
    }

    public static void releaseShader() {
        if(lighting)
            GlStateManager.enableLighting();
        useShader(0);
    }

    private static int createProgram(final ResourceLocation vert, final ResourceLocation frag) {
        int vertId = 0;
        if(vert != null)
            vertId = createShader(vert, VERT);

        int fragId = 0;
        if(frag != null)
            fragId = createShader(frag, FRAG);

        final int program = ARBShaderObjects.glCreateProgramObjectARB();
        if(program == 0)
            return 0;

        if(vert != null)
            ARBShaderObjects.glAttachObjectARB(program, vertId);
        if(frag != null)
            ARBShaderObjects.glAttachObjectARB(program, fragId);

        ARBShaderObjects.glLinkProgramARB(program);
        if(ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
            Log.getLogger().error(getLogInfo(program));
            return 0;
        }

        ARBShaderObjects.glValidateProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
            Log.getLogger().error(getLogInfo(program));
            return 0;
        }

        return program;
    }

    private static int createShader(final ResourceLocation filename, final int shaderType){
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

            if(shader == 0)
                return 0;

            ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(filename));
            ARBShaderObjects.glCompileShaderARB(shader);

            if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
                //noinspection ProhibitedExceptionThrown //Error creating shader.
                throw new RuntimeException("Error creating shader: " + getLogInfo(shader));

            return shader;
        }
        catch(final Exception e) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            Log.getLogger().error("Failed to create a shader.", e);
            return -1;
        }
    }

    private static String getLogInfo(final int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }

    private static String readFileAsString(final ResourceLocation filename) throws IOException
    {
        final InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(filename).getInputStream();

        if(in == null)
            return "";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

}
