package com.chitra.LibraryManagementService.service;

import com.chitra.LibraryManagementService.helper.BookTransformer;
import com.chitra.LibraryManagementService.model.Book;
import com.chitra.LibraryManagementService.model.BookEntity;
import com.chitra.LibraryManagementService.model.TagEntity;
import com.chitra.LibraryManagementService.repository.BookRepo;
import com.chitra.LibraryManagementService.repository.TagRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    @Autowired
    private final BookRepo bookRepo;

    @Autowired
    private final TagRepo tagRepo;

    public void deleteBook(String isbn) {
        bookRepo.deleteById(isbn);
    }
    public void updateBook(Book book, String isbn) {
        book.setIsbn(isbn);
        saveBook(book);
    }

    public List<Book> saveAllBooks(List<Book> bookList) {
        return bookList.stream().map(this::saveBook).collect(Collectors.toList());
    }

    public Book saveBook(Book book) {
        Set<String> newTags = CollectionUtils.isNotEmpty(book.getTags()) ? book.getTags() : new HashSet<>();
        BookEntity bookEntity = bookRepo.findById(book.getIsbn()).orElse(BookTransformer.buildBookEntityWithoutTags(book));
        Set<String> existingTags = bookEntity.getTagEntities().stream().map(TagEntity::getName).collect(Collectors.toSet());

        Set<String> tagsToRemove = existingTags.stream().filter(tag -> !newTags.contains(tag)).collect(Collectors.toSet());
        removeTags(bookEntity, tagsToRemove);

        Set<String> tagsToAdd = newTags.stream().filter(tag -> !existingTags.contains(tag)).collect(Collectors.toSet());
        addTags(bookEntity, tagsToAdd);

        BookEntity savedBookEntity = bookRepo.save(bookEntity);
        return BookTransformer.buildBook(savedBookEntity);
    }

    private void removeTags(BookEntity bookEntity, Set<String> tags) {
        tags.forEach(tag -> {
            bookEntity.getTagEntities().removeIf(x -> x.getName().equals(tag));
//            TagEntity existingTagEntity = tagRepo.findById(tag).get();
//            existingTagEntity.getBookEntities().removeIf(x -> x.getIsbn().equals(bookEntity.getIsbn()));
//            tagRepo.save(existingTagEntity);
        });
    }

    private void addTags(BookEntity bookEntity, Set<String> tags) {
        tags.forEach(tag -> {
            TagEntity tagEntity = TagEntity.builder().name(tag).bookEntities(new HashSet<>()).build();
            bookEntity.getTagEntities().add(tagEntity);
            TagEntity existingTagEntity = tagRepo.findById(tag).orElse(tagEntity);
//            existingTagEntity.getBookEntities().add(bookEntity);
            tagRepo.save(existingTagEntity);
        });
    }


}
