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
    public static void heapSort(int[] array) {
        int n = array.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }

        for (int i = n - 1; i >= 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            heapify(array, i, 0);
        }
    }

    /**
     * Heapifies a subtree rooted with node i which is an index in array[].
     *
     * @param array     The array representing the heap.
     * @param heapSize  The size of the heap.
     * @param rootIndex The index of the root of the subtree to be heapified.
     */
    private static void heapify(int[] array, int heapSize, int rootIndex) {
        int largest = rootIndex;
        int leftChild = 2 * rootIndex + 1;
        int rightChild = 2 * rootIndex + 2;

        if (leftChild < heapSize && array[leftChild] > array[largest]) {
            largest = leftChild;
        }

        if (rightChild < heapSize && array[rightChild] > array[largest]) {
            largest = rightChild;
        }

        if (largest != rootIndex) {
            int swap = array[rootIndex];
            array[rootIndex] = array[largest];
            array[largest] = swap;

            heapify(array, heapSize, largest);
        }
    }
}
