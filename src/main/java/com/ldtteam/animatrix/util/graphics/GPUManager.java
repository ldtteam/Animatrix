package com.ldtteam.animatrix.util.graphics;

import io.netty.util.internal.ConcurrentSet;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.concurrent.atomic.AtomicBoolean;

@SideOnly(Side.CLIENT)
public class GPUManager
{
    private static GPUManager ourInstance = new GPUManager();

    public static GPUManager getInstance()
    {
        return ourInstance;
    }

    private final ConcurrentSet<Integer>         clearableVbos = new ConcurrentSet<>();
    private final ConcurrentSet<Integer>         clearableVaos = new ConcurrentSet<>();

    private AtomicBoolean isScheduled;

    private GPUManager()
    {
    }

    /**
     * Creates a new VAO on the GPU.
     * @return The new VAO.
     */
    public VAO createVAO() {
        final int id = GL30.glGenVertexArrays();
        return new VAO(id);
    }

    /**
     * Creates a new VBO for the given type.
     *
     * @param type The type.
     * @return The VBO.
     */
    public VBO createVBO(final int type){
        final int id = GL15.glGenBuffers();
        return new VBO(id, type);
    }

    /**
     * Marks the given id of a VBO as deleteable.
     * @param vboId The id of the VBO that needs to be deleted.
     */
    void markVBOForClear(final int vboId)
    {
        clearableVbos.add(vboId);
        scheduleClearingOnMinecraftThread();
    }

    /**
     * Marks the given id of a VAO as deleteable.
     * @param vaoId The id of the VAO that needs to be deleted.
     */
    void markVAOForClear(final int vaoId)
    {
        clearableVaos.add(vaoId);
        scheduleClearingOnMinecraftThread();
    }

    /**
     * Schedules the clearing of the VBOs and VAO from the GPU on the Minecraft thread.
     */
    private void scheduleClearingOnMinecraftThread()
    {
        if (isScheduled.get())
            return;

        isScheduled.set(true);

        Minecraft.getMinecraft().addScheduledTask(() -> {
            clearableVaos.forEach(GL30::glDeleteVertexArrays);
            clearableVbos.forEach(GL15::glDeleteBuffers);
            isScheduled.set(false);
        });
    }
}
