package com.ldtteam.animatrix.util;

import net.minecraft.util.ResourceLocation;

/**
 * Holds the constants for Animatrix.
 */
public final class Constants
{

    private Constants()
    {
        throw new IllegalArgumentException("Utility Class");
    }

    @SuppressWarnings("NewClassNamingConvention") // General too short.
    /**
     * Contains all general constants in use by Animatrix.
     */
    public static final class General
    {
        private General() {}

        public static final String MOD_ID = "animatrix";
        public static final String MOD_NAME = "Animatrix";
        public static final String MOD_VERSION = "@VERSION@";
    }

    public static final class Shaders
    {
        public static final class Vertex
        {
            public static final ResourceLocation ANIMATRIX_DEFAULT = new ResourceLocation(General.MOD_ID, "shaders/animatrixVertex.glsl");
        }
    }

}