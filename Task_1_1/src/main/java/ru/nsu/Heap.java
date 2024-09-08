package ru.nsu;


/**
 * Max-Heap class.
 */
public class Heap {

    /**
     * Converts array {@code array} into max-heap.
     *
     * @param array would be heapified.
     */
    public static void heapify(int[] array) {
        int len = array.length;
        int start = len / 2;

        for (int i = start; i >= 0; i--) {
            siftDown(array, len, i);
        }
    }

    /**
     * Swaps two elements in {@code array}.
     *
     * @param array where to swap.
     * @param i     first index.
     * @param j     second index.
     */
    public static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }


    /**
     * Sifts down element in {@code heap} on position {@code index}.
     *
     * @param array where to sift.
     * @param len   len of heap.
     * @param index index of element.
     */
    public static void siftDown(int[] array, int len, int index) {
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int largest = index;

        if (left < len && array[index] < array[left]) {
            largest = left;
        }
        if (right < len && array[largest] < array[right]) {
            largest = right;
        }
        if (largest != index) {
            swap(array, index, largest);
            siftDown(array, len, largest);
        }
    }
}
