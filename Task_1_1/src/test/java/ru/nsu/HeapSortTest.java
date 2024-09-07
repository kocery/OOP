package ru.nsu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Unit tests for the HeapSort class
 */
public class HeapSortTest {

    /**
     * Tests HeapSort with an array of integers
     */
    @Test
    public void testHeapSort() {
        int[] unsortedArray = {4, 10, 3, 5, 1};
        int[] sortedArray = {1, 3, 4, 5, 10};

        HeapSort.heapSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    /**
     * Tests HeapSort with an already sorted array
     */
    @Test
    public void testHeapSortAlreadySorted() {
        int[] alreadySortedArray = {1, 2, 3, 4, 5};
        int[] expectedArray = {1, 2, 3, 4, 5};

        HeapSort.heapSort(alreadySortedArray);
        assertArrayEquals(expectedArray, alreadySortedArray);
    }

    /**
     * Tests HeapSort with an array of identical elements
     */
    @Test
    public void testHeapSortIdenticalElements() {
        int[] identicalArray = {5, 5, 5, 5, 5};
        int[] expectedArray = {5, 5, 5, 5, 5};

        HeapSort.heapSort(identicalArray);
        assertArrayEquals(expectedArray, identicalArray);
    }

    /**
     * Tests HeapSort with an empty array
     */
    @Test
    public void testHeapSortEmptyArray() {
        int[] emptyArray = {};
        int[] expectedArray = {};

        HeapSort.heapSort(emptyArray);
        assertArrayEquals(expectedArray, emptyArray);
    }

    /**
     * Tests HeapSort with an array containing a single element
     */
    @Test
    public void testHeapSortSingleElement() {
        int[] singleElementArray = {42};
        int[] expectedArray = {42};

        HeapSort.heapSort(singleElementArray);
        assertArrayEquals(expectedArray, singleElementArray);
    }
}
