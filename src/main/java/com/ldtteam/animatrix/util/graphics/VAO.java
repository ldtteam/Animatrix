package com.ldtteam.animatrix.util.graphics;

import com.google.common.collect.Lists;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.Collection;

/**
 * Represents a Vertex Array Object
 */
public class VAO
{
    private static final int             BYTES_PER_FLOAT = 4;
    private static final int             BYTES_PER_INT   = 4;
    public final         int             id;
    private              Collection<VBO> dataVBOs        = Lists.newArrayList();
    private              VBO             indexVbo;
    private              int             indexCount;

    public static VAO create() {
        final int id = GL30.glGenVertexArrays();
        return new VAO(id);
    }

    private VAO(final int id) {
        this.id = id;
    }

    public int getIndexCount(){
        return indexCount;
    }

    public void bind(final int... attributes){
        bind();
        for (final int i : attributes) {
            GL20.glEnableVertexAttribArray(i);
        }
    }

    public void unbind(final int... attributes){
        for (final int i : attributes) {
            GL20.glDisableVertexAttribArray(i);
        }
        unbind();
    }

    public void createIndexBuffer(final int[] indices){
        this.indexVbo = VBO.create(GL15.GL_ELEMENT_ARRAY_BUFFER);
        indexVbo.bind();
        indexVbo.storeData(indices);
        this.indexCount = indices.length;
    }

    public void createAttribute(final int attribute, final float[] data, int attrSize){
        final VBO dataVbo = VBO.create(GL15.GL_ARRAY_BUFFER);
        dataVbo.bind();
        dataVbo.storeData(data);
        GL20.glVertexAttribPointer(attribute, attrSize, GL11.GL_FLOAT, false, attrSize * BYTES_PER_FLOAT, 0);
        dataVbo.unbind();
        dataVBOs.add(dataVbo);
    }

    public void createIntAttribute(final int attribute, final int[] data, int attrSize){
        final VBO dataVbo = VBO.create(GL15.GL_ARRAY_BUFFER);
        dataVbo.bind();
        dataVbo.storeData(data);
        GL30.glVertexAttribIPointer(attribute, attrSize, GL11.GL_INT, attrSize * BYTES_PER_INT, 0);
        dataVbo.unbind();
        dataVBOs.add(dataVbo);
    }

    public void delete() {
        GL30.glDeleteVertexArrays(id);
        for(final VBO vbo : dataVBOs){
            vbo.delete();
        }
        indexVbo.delete();
    }

    private void bind() {
        GL30.glBindVertexArray(id);
    }

    private void unbind() {
        GL30.glBindVertexArray(0);
    }

}
