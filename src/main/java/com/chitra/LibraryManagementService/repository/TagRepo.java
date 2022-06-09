package com.chitra.LibraryManagementService.repository;

import com.chitra.LibraryManagementService.model.TagEntity;
import org.springframework.data.repository.CrudRepository;

public interface TagRepo extends CrudRepository<TagEntity, String> {
}
