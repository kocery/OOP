package substringfinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;


public class SubstringFinderTest {

    private String testFilename;

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        testFilename = testInfo.getTestMethod().get().getName() + "_test_file.txt";
    }

    @AfterEach
    public void tearDown() {
        File file = new File(testFilename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testFindSubstringOccurrences() throws IOException {
        String substring = "abc";
        int repetitions = 1000;
        generateLargeTestFile(testFilename, substring, repetitions);

        List<Long> occurrences = SubstringFinder.findSubstringOccurrences(testFilename, substring);
        List<Long> expectedOccurrences = generateExpectedOccurrences(substring.length(),
            repetitions);

        assertEquals(repetitions, occurrences.size(),
            "The number of occurrences does not match the expected value");
        assertEquals(expectedOccurrences, occurrences,
            "The indices of occurrences do not match the expected values");
    }

    @Test
    public void testFindSubstringOccurrencesWithUnicode() throws IOException {
        String unicodeString = "😊"; // Code point outside BMP
        int repetitions = 5000;
        generateLargeTestFile(testFilename, unicodeString, repetitions);

        List<Long> occurrences = SubstringFinder.findSubstringOccurrences(testFilename,
            unicodeString);
        List<Long> expectedOccurrences = generateExpectedOccurrences(1,
            repetitions);

        assertEquals(repetitions, occurrences.size(),
            "The number of Unicode character occurrences does not match the expected value");
        assertEquals(expectedOccurrences, occurrences,
            "The indices of Unicode occurrences do not match the expected values");
    }

    @Test
    public void testSubstringNotFound() throws IOException {
        String substring = "xyz";
        generateLargeTestFile(testFilename, "abc", 1000);

        List<Long> occurrences = SubstringFinder.findSubstringOccurrences(testFilename, substring);

        assertTrue(occurrences.isEmpty(), "No occurrences should be found");
    }

    private static void generateLargeTestFile(String filename, String pattern, int repetitions)
        throws IOException {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(filename),
            StandardCharsets.UTF_8)) {
            for (int i = 0; i < repetitions; i++) {
                writer.write(pattern + " ");
            }
        }
    }

    private static List<Long> generateExpectedOccurrences(int patternLength, int repetitions) {
        List<Long> expectedIndices = new ArrayList<>();
        int gap = patternLength + 1; // Pattern length + 1 space
        for (int i = 0; i < repetitions; i++) {
            expectedIndices.add((long) i * gap);
        }
        return expectedIndices;
    }

    @Test
    public void testSingleCharacterPattern() throws IOException {
        String content = "aaaaaa";
        String pattern = "a";
        createTestFile(testFilename, content);

        List<Long> occurrences = SubstringFinder.findSubstringOccurrences(testFilename, pattern);

        // Expected indices: 0, 1, 2, 3, 4, 5
        List<Long> expectedOccurrences = List.of(0L, 1L, 2L, 3L, 4L, 5L);
        assertEquals(expectedOccurrences, occurrences,
            "The indices of single character occurrences do not match the expected values");
    }

    @Test
    public void testOverlappingPattern() throws IOException {
        String content = "ababababa";
        String pattern = "aba";
        createTestFile(testFilename, content);

        List<Long> occurrences = SubstringFinder.findSubstringOccurrences(testFilename, pattern);

        // Expected indices: 0, 2, 4, 6
        List<Long> expectedOccurrences = List.of(0L, 2L, 4L, 6L);
        assertEquals(expectedOccurrences, occurrences,
            "The indices of overlapping pattern occurrences do not match the expected values");
    }

    @Test
    public void testEmptyPattern() throws IOException {
        String content = "abcde";
        String pattern = "";
        createTestFile(testFilename, content);

        List<Long> occurrences = SubstringFinder.findSubstringOccurrences(testFilename, pattern);

        assertTrue(occurrences.isEmpty(),
            "The occurrences of an empty pattern should be an empty list");
    }

    @Test
    public void testEmptyString() throws IOException {
        String content = "";
        String pattern = "a";
        createTestFile(testFilename, content);

        List<Long> occurrences = SubstringFinder.findSubstringOccurrences(testFilename, pattern);

        assertTrue(occurrences.isEmpty(),
            "The occurrences of an empty pattern should be an empty list");
    }

    private static void createTestFile(String filename, String content) throws IOException {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(filename),
            StandardCharsets.UTF_8)) {
            writer.write(content);
        }
    }
}