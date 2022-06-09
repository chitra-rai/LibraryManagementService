package com.chitra.LibraryManagementService.controller;

import com.chitra.LibraryManagementService.model.Book;
import com.chitra.LibraryManagementService.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    private static final String TEST_ISBN = "1234";
    private static final String TEST_AUTHOR = "John";
    private static final String TEST_TITLE = "Treasure Island";

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    void testFindBookByIsbnWhenBookExists() {
        when(bookService.findBookByIsbn(TEST_ISBN)).thenReturn(buildBook());
        assertThat(bookController.findBookByIsbn(TEST_ISBN)).isEqualTo(buildBook());
    }

    @Test
    void testFindBookByIsbnWhenBookNotExists() {
        when(bookService.findBookByIsbn(TEST_ISBN)).thenReturn(null);
        assertThat(bookController.findBookByIsbn(TEST_ISBN)).isNull();
    }

    @Test
    void testFindBookByIsbnWhenIsbnNull() {
        assertThat(bookController.findBookByIsbn(null)).isNull();
    }

    @Test
    void testFindBookByIsbnWhenIsbnHasOnlySpaces() {
        assertThat(bookController.findBookByIsbn("     ")).isNull();
    }

    @Test
    void testFindBookByIsbnWhenBookExistsAndInputHasSpaces() {
        when(bookService.findBookByIsbn(TEST_ISBN)).thenReturn(buildBook());
        assertThat(bookController.findBookByIsbn("    " + TEST_ISBN + "   ")).isEqualTo(buildBook());
    }

    @Test
    void testFindAllBooksWhenBooksPresent() {
        when(bookService.findAllBooks()).thenReturn(List.of(buildBook()));
        List<Book> response = bookController.findAllBooks();
        assertThat(response.size()).isOne();
        assertThat(response.get(0)).isEqualTo(buildBook());
    }

    @Test
    void testFindAllBooksWhenBooksNotPresent() {
        when(bookService.findAllBooks()).thenReturn(Collections.emptyList());
        List<Book> response = bookController.findAllBooks();
        assertThat(response.size()).isZero();
    }

    @Test
    void testSaveBookSuccessfully() {
        when(bookService.saveBook(buildBook())).thenReturn(buildBook());
        assertThat(bookController.createBook(buildBook())).isEqualTo(buildBook());
    }

    @Test
    void testUpdateBookSuccessfully() {
        bookController.updateBook(buildBook(), TEST_ISBN);
        verify(bookService).updateBook(buildBook(), TEST_ISBN);
    }

    @Test
    void testDeleteBookSuccessfully() {
        bookController.deleteBook(TEST_ISBN);
        verify(bookService).deleteBook(TEST_ISBN);
    }

    private Book buildBook() {
        return Book.builder()
                .isbn(TEST_ISBN)
                .author(TEST_AUTHOR)
                .title(TEST_TITLE)
                .build();
    }
}