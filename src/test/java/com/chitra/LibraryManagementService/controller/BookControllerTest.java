package com.chitra.LibraryManagementService.controller;

import com.chitra.LibraryManagementService.repository.BookRepo;
import com.chitra.LibraryManagementService.model.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    private static final String TEST_ISBN = "1234";
    private static final String TEST_AUTHOR = "John";
    private static final String TEST_TITLE = "Treasure Island";

    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private BookController bookController;

    @Test
    void getBookWhenBookExists() {
        when(bookRepo.findById(TEST_ISBN)).thenReturn(Optional.of(buildBook()));
        assertThat(bookController.findByIsbn(TEST_ISBN)).isEqualTo(buildBook());
    }

    @Test
    void getBookWhenBookNotExists() {
        when(bookRepo.findById(TEST_ISBN)).thenReturn(Optional.empty());
        assertThat(bookController.findByIsbn(TEST_ISBN)).isNull();
    }

    @Test
    void getBookWhenIsbnNull() {
        assertThat(bookController.findByIsbn(null)).isNull();
    }

    @Test
    void getBookWhenIsbnHasOnlySpaces() {
        assertThat(bookController.findByIsbn("     ")).isNull();
    }

    @Test
    void getBookWhenBookExistsAndInputHasSpaces() {
        when(bookRepo.findById(TEST_ISBN)).thenReturn(Optional.of(buildBook()));
        assertThat(bookController.findByIsbn("    " + TEST_ISBN + "   ")).isEqualTo(buildBook());
    }

    private BookEntity buildBook() {
        return BookEntity.builder()
                .isbn(TEST_ISBN)
                .author(TEST_AUTHOR)
                .title(TEST_TITLE).build();
    }
}