package com.chitra.LibraryManagementService.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class BookEntity {
    @Id
    private String isbn;
    private String title;
    private String author;

    @ManyToMany
    @JoinTable(
            name = "book_tags",
            joinColumns = @JoinColumn(name = "isbn"),
            inverseJoinColumns = @JoinColumn(name = "name"))
    private Set<TagEntity> tagEntities;
}
