package com.chitra.LibraryManagementService.controller;

import com.chitra.LibraryManagementService.model.Book;
import com.chitra.LibraryManagementService.model.BookEntity;
import com.chitra.LibraryManagementService.repository.BookRepo;
import com.chitra.LibraryManagementService.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepo bookRepo;

    @GetMapping
    public List<BookEntity> findAllBooks() {
        List<BookEntity> response = new ArrayList<>();
        bookRepo.findAll().forEach(response::add);
        return response;
    }

    @GetMapping("/{isbn}")
    public BookEntity findByIsbn(@PathVariable String isbn) {
        if (isbn == null || isbn.isBlank()) {
            return null;
        }
        return bookRepo.findById(isbn.trim()).orElse(null);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Book createBook(@Valid @RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{isbn}")
    public void updateBook(@RequestBody Book book, @PathVariable String isbn) {
        bookService.updateBook(book, isbn);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{isbn}")
    public void deleteBook(@PathVariable String isbn) {
        bookService.deleteBook(isbn);
    }
}
