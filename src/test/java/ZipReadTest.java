import com.codeborne.pdftest.PDF;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ZipReadTest {
    private final ClassLoader cl = ZipReadTest.class.getClassLoader();

    @Test
    void zipFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                Objects.requireNonNull(cl.getResourceAsStream("test.zip"))
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".pdf")) {
                    PDF pdf = new PDF(zis);
                    String actualPdfText = pdf.text;
                    String expectedPdfText = "Пример документа в формате PDF";
                    assertThat(actualPdfText).contains(expectedPdfText);
                }
            }
        }
    }
}
