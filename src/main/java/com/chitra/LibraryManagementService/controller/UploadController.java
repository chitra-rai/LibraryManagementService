package com.chitra.LibraryManagementService.controller;

import com.chitra.LibraryManagementService.helper.CsvHelper;
import com.chitra.LibraryManagementService.model.Book;
import com.chitra.LibraryManagementService.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private BookService bookService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public List<Book> uploadFile(@RequestParam("file") MultipartFile file) {
        if (!CsvHelper.hasCSVFormat(file)) {
            return Collections.emptyList();
        }

        try {
            List<Book> bookList = CsvHelper.csvToBookEntity(file.getInputStream());
            return bookService.saveAllBooks(bookList);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to upload CSV file. Reason: %s", e.getMessage()));
        }
    }
}
