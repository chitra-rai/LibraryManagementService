package com.chitra.LibraryManagementService.controller;

import com.chitra.LibraryManagementService.model.Book;
import com.chitra.LibraryManagementService.model.BookEntity;
import com.chitra.LibraryManagementService.model.TagEntity;
import com.chitra.LibraryManagementService.repository.BookRepo;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private BookRepo bookRepo;

    @GetMapping
    public List<Book> findBooks(
            @And({
                    @Spec(path = "isbn", spec = Equal.class),
                    @Spec(path = "author", spec = Equal.class),
                    @Spec(path = "title", spec = Equal.class)
            }) Specification<BookEntity> bookSpec) {
        List<BookEntity> bookEntityList = bookRepo.findAll(bookSpec);
        return bookEntityList.stream().map(bookEntity ->
                        Book.builder().isbn(bookEntity.getIsbn())
                                .title(bookEntity.getTitle())
                                .author(bookEntity.getAuthor())
                                .tags(bookEntity.getTagEntities().stream().map(TagEntity::getName).collect(Collectors.toSet()))
                                .build())
                .collect(Collectors.toList());
    }
}
