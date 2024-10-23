package substringfinder;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for finding all occurrences of a given substring within a file.
 */
public class SubstringFinder {

    /**
     * Searches for all occurrences of a given substring in a file and returns a list of starting
     * indices.
     *
     * @param filename  the name of the file to search in.
     * @param substring the substring to search for (may contain characters from any UTF-8
     *                  alphabet).
     * @return a list of starting indices of each occurrence of the substring.
     * @throws IOException if an I/O error occurs while reading the file.
     */
    public static List<Long> findSubstringOccurrences(String filename, String substring)
        throws IOException {
        // Convert the substring to an array of Unicode code points
        int[] pattern = substring.codePoints().toArray();
        int patternLength = pattern.length;

        // Build the failure function for the Knuth-Morris-Pratt (KMP) algorithm
        int[] failure = buildFailureFunction(pattern);

        List<Long> occurrences = new ArrayList<>();

        try (Reader reader = new InputStreamReader(new FileInputStream(filename),
            StandardCharsets.UTF_8)) {
            int codePoint;
            int j = 0; // Index for pattern[]
            long index = 0; // Index of the current code point in the file

            while ((codePoint = readCodePoint(reader)) != -1) {
                while (j > 0 && codePoint != pattern[j]) {
                    j = failure[j - 1];
                }
                if (codePoint == pattern[j]) {
                    j++;
                }
                if (j == patternLength) {
                    occurrences.add(index - patternLength + 1);
                    j = failure[j - 1];
                }
                index++;
            }
        }
        return occurrences;
    }

    /**
     * Reads one code point from the Reader, correctly handling surrogate pairs.
     *
     * @param reader the Reader to read data from.
     * @return the next code point, or -1 if the end of the stream is reached.
     * @throws IOException if an I/O error occurs.
     */
    private static int readCodePoint(Reader reader) throws IOException {
        int c1 = reader.read();
        if (c1 == -1) {
            return -1; // End of stream
        }
        char ch1 = (char) c1;
        if (Character.isHighSurrogate(ch1)) {
            int c2 = reader.read();
            if (c2 != -1) {
                char ch2 = (char) c2;
                if (Character.isLowSurrogate(ch2)) {
                    return Character.toCodePoint(ch1, ch2);
                } else {
                    return ch1;
                }
            } else {
                return ch1;
            }
        } else {
            return ch1;
        }
    }

    /**
     * Builds the failure function for the KMP algorithm.
     *
     * @param pattern the array of code points of the substring.
     * @return the failure function array.
     */
    private static int[] buildFailureFunction(int[] pattern) {
        int[] failure = new int[pattern.length];
        int j = 0;

        for (int i = 1; i < pattern.length; i++) {
            while (j > 0 && pattern[i] != pattern[j]) {
                j = failure[j - 1];
            }
            if (pattern[i] == pattern[j]) {
                j++;
            }
            failure[i] = j;
        }
        return failure;
    }
}