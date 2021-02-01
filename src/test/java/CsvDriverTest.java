import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

class CsvDriverTest {

    public final static Map<String, String> AUTHOR_BOOK_MAP =
            Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
                {
                    put("Dan Simmons", "Hyperion");
                    put("Douglas Adams", "The Hitchhiker's Guide to the Galaxy");
                }
            });
    public final static String[] HEADERS = {"author", "title"};
    public final static String EXPECTED_FILESTREAM =
            "author,title\r\n" + "Dan Simmons,Hyperion\r\n" + "Douglas Adams,The Hitchhiker's Guide to the Galaxy";

    @Test
    void testReadCsv() throws IOException {
        Reader in = new FileReader(
                "E:\\personal-projects\\java-codes\\apache-commons-csv-poc\\src\\test\\resources\\books.csv");
        Iterable<CSVRecord> csvRecords = CSVFormat.DEFAULT
                .withHeader(HEADERS)
                .withFirstRecordAsHeader()
                .parse(in);
        for (CSVRecord record : csvRecords) {
            String author = record.get("author");
            String title = record.get("title");
            Assertions.assertEquals(AUTHOR_BOOK_MAP.get(author), title);
        }
    }

    @Test
    void testWritingCsvFile() throws IOException {
        FileWriter out = new FileWriter(
                "E:\\personal-projects\\java-codes\\apache-commons-csv-poc\\src\\test" + "\\resources\\books_new.csv");
        CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS));
        AUTHOR_BOOK_MAP.forEach((author, title) -> {
            try {
                printer.printRecord(author, title);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}