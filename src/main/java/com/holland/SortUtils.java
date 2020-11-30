package com.holland;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class SortUtils {
    /**
     * time complexity(avg): O(n)^2
     * time complexity(worst):O(n)^2
     * time complexity(best): O(n)
     * space complexity: O(1)
     * stability: true
     */
    public static int[] bubbleSort(@Nullable int[] array) {
        if (array == null) return null;

        int len = array.length - 1;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - i; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j);
                }
            }
        }
        return array;
    }

    /**
     * todo
     * time complexity(avg): O(n)^2
     * time complexity(worst):O(n)^2
     * time complexity(best): O(n)^2
     * space complexity: O(1)
     * stability: false
     */
    public static int[] selectionSort(@Nullable int[] array) {
        if (array == null) return null;

        int len = array.length - 1;
        for (int j = 0; j < len; j++) {
            int minimumIndex = j;
            for (int i = j; i < len; i++) {
                minimumIndex = array[minimumIndex] > array[i + 1] ? i + 1 : minimumIndex;
            }
            swap(array, j, minimumIndex);
        }
        return array;
    }

    @SuppressWarnings("PrimitiveArrayArgumentToVarargsMethod")
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method sort = SortUtils.class.getMethod("selectionSort", int[].class);
        int[] a0 = null;
        //noinspection ConstantConditions
        System.out.println(Arrays.toString((int[]) sort.invoke(SortUtils.class, a0)));
        int[] a1 = {};
        System.out.println(Arrays.toString((int[]) sort.invoke(SortUtils.class, a1)));
        int[] a2 = {3, 1, 2, 6, 8, 5, 0, 15, 1};
        System.out.println(Arrays.toString((int[]) sort.invoke(SortUtils.class, a2)));
    }

    private static void swap(@NotNull int[] array, int index) {
        swap(array, index, index + 1);
    }

    private static void swap(@NotNull int[] array, int index1, int index2) {
        if (index1 == index2) return;
        array[index1] += array[index2];
        array[index2] = array[index1] - array[index2];
        array[index1] -= array[index2];
    }
}
