package com.holland.util;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@SuppressWarnings("unused")
public class SortUtils {
    /**
     * time complexity(avg): O(n)^2
     * time complexity(worst):O(n)^2
     * time complexity(best): O(n)
     * space complexity: O(1)
     * stability: true
     */
    public static int[] bubbleSort(@Nullable final int[] array) {
        if (array == null) return null;
        final int len = array.length - 1;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - i; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                }
            }
        }
        return array;
    }

    /**
     * time complexity(avg): O(n)^2
     * time complexity(worst):O(n)^2
     * time complexity(best): O(n)^2
     * space complexity: O(1)
     * stability: false
     */
    public static int[] selectionSort(@Nullable final int[] array) {
        if (array == null) return null;
        final int len = array.length - 1;
        for (int j = 0; j < len; j++) {
            int minimumIndex = j;
            for (int i = j; i < len; i++) {
                minimumIndex = array[minimumIndex] > array[i + 1] ? i + 1 : minimumIndex;
            }
            swap(array, j, minimumIndex);
        }
        return array;
    }

    /**
     * time complexity(avg): O(n)^2
     * time complexity(worst): O(n)^2
     * time complexity(best): O(n)
     * space complexity: O(1)
     * stability: true
     */
    public static int[] insertionSort(@Nullable final int[] array) {
        if (array == null) return null;
        final int len = array.length;
        for (int i = 1; i < len; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j] < array[j - 1]) swap(array, j, j - 1);
                else break;
            }
        }
        return array;
    }

//    public static void main(String[] args) throws NoSuchMethodException {
//        final Method sort = SortUtils.class.getMethod("insertionSort", int[].class);
//        final int[] a0 = null;
//        //noinspection ConstantConditions
//        solution(sort, a0);
//        final int[] a1 = {};
//        solution(sort, a1);
//        final int[] a2 = {3, 1, 2, 6, 8, 5, 0, 15, 1};
//        solution(sort, a2);
//    }

    private static void solution(final Method method, final int[] array) {
        try {
            //noinspection PrimitiveArrayArgumentToVarargsMethod
            System.out.println(Arrays.toString((int[]) method.invoke(SortUtils.class, array)));
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println(e.getClass().getName());
            System.exit(-1);
        }
    }

    private static void swap(@NotNull final int[] array, final int index1, final int index2) {
        if (index1 == index2) return;
        array[index1] += array[index2];
        array[index2] = array[index1] - array[index2];
        array[index1] -= array[index2];
    }
}
