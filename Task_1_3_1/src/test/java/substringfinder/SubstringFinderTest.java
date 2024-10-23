package substringfinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
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
        generateLargeTestFile(testFilename, substring, 1000000);

        List<Long> occurrences = SubstringFinder.findSubstringOccurrences(testFilename, substring);

        assertEquals(1000000, occurrences.size(),
            "The number of occurrences does not match the expected value");
    }

    @Test
    public void testFindSubstringOccurrencesWithUnicode() throws IOException {
        String unicodeString = "😊"; // Code point outside BMP
        generateLargeTestFile(testFilename, unicodeString, 5000);

        List<Long> occurrences = SubstringFinder.findSubstringOccurrences(testFilename,
            unicodeString);

        assertEquals(5000, occurrences.size(),
            "The number of Unicode character occurrences does not match the expected value");
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
}