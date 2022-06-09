package com.chitra.LibraryManagementService.helper;

import com.chitra.LibraryManagementService.model.Book;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CSVHelper {
    public static final String TYPE = "text/csv";
    private static final String HEADER_ISBN = "ISBN";
    private static final String HEADER_TITLE = "Title";
    private static final String HEADER_AUTHOR = "Author";
    private static final String HEADER_TAGS = "Tags";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Book> csvToBookEntity(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<Book> bookList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Set<String> tags = null;
                if (StringUtils.isNotBlank(csvRecord.get(HEADER_TAGS))) {
                    tags = Arrays.stream(csvRecord.get(HEADER_TAGS).split(" ")).collect(Collectors.toSet());
                }

                Book book = Book.builder()
                        .isbn(csvRecord.get(HEADER_ISBN))
                        .title(csvRecord.get(HEADER_TITLE))
                        .author(csvRecord.get(HEADER_AUTHOR))
                        .tags(tags)
                        .build();

                bookList.add(book);
            }
            return bookList;
        } catch (IOException e) {
            throw new RuntimeException("failed to parse CSV file: " + e.getMessage());
        }
    }
}