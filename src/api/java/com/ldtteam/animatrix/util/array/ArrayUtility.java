package com.ldtteam.animatrix.util.array;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public final class ArrayUtility
{

    private ArrayUtility()
    {
        throw new IllegalArgumentException("Utility Class");
    }

    public static <T> void InitializeArray(@NotNull final T[] array, @NotNull final Supplier<T> supplier)
    {
        for (int i = 0; i < array.length; i++)
        {
            array[i] = supplier.get();
        }
    }
}