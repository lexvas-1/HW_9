import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ZipReadTest {
    private final ClassLoader cl = ZipReadTest.class.getClassLoader();

    @Test
    void pdfFileParsingTest() throws Exception {
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


    @Test
    void csvFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                Objects.requireNonNull(cl.getResourceAsStream("test.zip"))
        )) {

            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".csv")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> data = reader.readAll();
                    Assertions.assertEquals(4, data.size());
                    Assertions.assertArrayEquals(
                            new String[]{"Name", "Job Title", "Address", "State", "City"},
                            data.get(0)
                    );
                    Assertions.assertArrayEquals(
                            new String[]{"Edward Green", "Developer", "110 Pike Street", "WA", "Seattle"},
                            data.get(3)
                    );
                }
            }
        }
    }

    @Test
    void xlsFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                Objects.requireNonNull(cl.getResourceAsStream("test.zip"))
        )) {

            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".xls")) {
                    XLS xls = new XLS(zis);
                     String actualCellValue = xls.excel.getSheetAt(0).getRow(5).getCell(1).getStringCellValue();
                     String expectedCellValue = "Mexico";
                     Assertions.assertEquals(expectedCellValue, actualCellValue);
                }
            }
        }
    }
}
