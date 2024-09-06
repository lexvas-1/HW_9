import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
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
        InputStream zipInputStream = cl.getResourceAsStream("test.zip");
        assertThat(zipInputStream).as("ZIP file should exist").isNotNull();

        assert zipInputStream != null;
        try (ZipInputStream zis = new ZipInputStream(zipInputStream)) {
            ZipEntry entry;
            boolean pdfFound = false; // Инициализируем как false

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".pdf")) { // Проверка на окончание .pdf
                    pdfFound = true; // Установите в true, если найдена PDF
                    // Чтение PDF
                    PDF pdf = new PDF(zis);
                    String actualPdfText = pdf.text; // Убедитесь, что метод getText есть
                    String expectedPdfText = "Пример документа в формате PDF";
                    assertThat(actualPdfText).contains(expectedPdfText);
                }
            }

            assertThat(pdfFound).as("Не найдено ни одного PDF-файла в архиве.").isTrue(); // Проверка после цикла
        }
    }


    @Test
    void csvFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                Objects.requireNonNull(cl.getResourceAsStream("test.zip"))
        )) {

            ZipEntry entry;
            boolean csvFound = false;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".csv")) {
                    csvFound = true;
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
            assertThat(csvFound).as("Не найдено ни одного PDF-файла в архиве.").isTrue();
        }
    }

    @Test
    void xlsFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                Objects.requireNonNull(cl.getResourceAsStream("test.zip"))
        )) {

            ZipEntry entry;
            boolean xlsFound = false;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".xls")) {
                    xlsFound = true;
                    XLS xls = new XLS(zis);
                     String actualCellValue = xls.excel.getSheetAt(0).getRow(5).getCell(1).getStringCellValue();
                     String expectedCellValue = "Mexico";
                     Assertions.assertEquals(expectedCellValue, actualCellValue);
                }
            }

            assertThat(xlsFound).as("Не найдено ни одного PDF-файла в архиве.").isTrue();
    }
}
}