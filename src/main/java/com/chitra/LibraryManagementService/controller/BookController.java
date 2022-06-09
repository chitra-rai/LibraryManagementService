package com.chitra.LibraryManagementService.controller;

import com.chitra.LibraryManagementService.model.Book;
import com.chitra.LibraryManagementService.repository.BookRepo;
import com.chitra.LibraryManagementService.service.BookService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{isbn}")
    public Book findBookByIsbn(@PathVariable String isbn) {
        if (StringUtils.isBlank(isbn)) {
            return null;
        }
        return bookService.findBookByIsbn(isbn.trim());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Book createBook(@Valid @RequestBody Book book) {
        if (book.getIsbn() == null || book.getTitle() == null || book.getAuthor() == null) {
            return null;
        }
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
