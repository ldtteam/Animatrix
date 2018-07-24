package com.ldtteam.animatrix.util;

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
}