package com.ldtteam.animatrix.handler;

import com.mojang.blaze3d.matrix.MatrixStack;

public final class GlobalRenderHandler {

    private static final GlobalRenderHandler INSTANCE = new GlobalRenderHandler();

    public static GlobalRenderHandler getInstance() {
        return INSTANCE;
    }

    private MatrixStack globalMatrixStack = new MatrixStack();

    private GlobalRenderHandler() {
    }

    public MatrixStack getGlobalMatrixStack() {
        return globalMatrixStack;
    }

    public void setGlobalMatrixStack(final MatrixStack globalMatrixStack) {
        this.globalMatrixStack = globalMatrixStack;
    }
}
