package com.chitra.LibraryManagementService.service;

import com.chitra.LibraryManagementService.repository.BookRepo;
import com.chitra.LibraryManagementService.repository.TagRepo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private TagRepo tagRepo;

    @InjectMocks
    private BookService bookService;

    // ToDo: add unit-tests
}
