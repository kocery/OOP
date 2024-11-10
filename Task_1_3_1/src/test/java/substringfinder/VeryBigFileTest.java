package substringfinder;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class VeryBigFileTest {

    private static final String LARGE_TEST_FILENAME = "large_test_file.txt";

    @AfterEach
    public void tearDown() {
        File file = new File(LARGE_TEST_FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testGigabyteFile() throws IOException {
        generateLargeFile(1024 * 1024 * 1024); // 1 GB file

        String pattern = "pattern_not_present";

        List<Long> occurrences = SubstringFinder.findSubstringOccurrences(LARGE_TEST_FILENAME,
            pattern);

        // Expected: No occurrences, as the pattern is not present in the file
        assertTrue(occurrences.isEmpty(),
            "The list of occurrences should be empty for a non-existing pattern");
    }

    private static void generateLargeFile(long size) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
            VeryBigFileTest.LARGE_TEST_FILENAME))) {
            byte[] buffer = new byte[1024 * 1024]; // 1 MB buffer
            for (int i = 0; i < buffer.length; i++) {
                buffer[i] = 'x';
            }
            long written = 0;
            while (written < size) {
                bos.write(buffer);
                written += buffer.length;
            }
        }
    }
}

