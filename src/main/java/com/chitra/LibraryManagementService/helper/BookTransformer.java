package com.chitra.LibraryManagementService.helper;

import com.chitra.LibraryManagementService.model.Book;
import com.chitra.LibraryManagementService.model.BookEntity;
import com.chitra.LibraryManagementService.model.TagEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BookTransformer {
    public static Book buildBook(BookEntity bookEntity) {
        return Book.builder()
                .isbn(bookEntity.getIsbn())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .tags(bookEntity.getTagEntities().stream().map(TagEntity::getName).collect(Collectors.toSet()))
                .build();
    }

    public static BookEntity buildBookEntityWithoutTags(Book book) {
        return BookEntity.builder()
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .title(book.getTitle())
                .tagEntities(new HashSet<>())
                .build();
    }
}
