package com.chitra.LibraryManagementService.repository;

import com.chitra.LibraryManagementService.model.BookEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface BookRepo extends CrudRepository<BookEntity, String>, JpaSpecificationExecutor<BookEntity> {
}
