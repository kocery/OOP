package ru.nsu;

/**
 * A class that implements the Heap Sort algorithm.
 */
public class HeapSort {

    /**
     * Performs Heap Sort on the provided array.
     *
     * @param array The array to be sorted.
     */
    static void heapSort(int[] array) {
        Heap.heapify(array);
        int len = array.length;
        for (int i = len - 1; i > 0; i--) {
            Heap.swap(array, 0, i);
            Heap.siftDown(array, i, 0);
        }
    }
}
