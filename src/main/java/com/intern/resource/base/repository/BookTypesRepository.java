package com.intern.resource.base.repository;

import com.intern.resource.base.entity.BookTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTypesRepository extends JpaRepository<BookTypes, Long> {
    boolean existsByName(String name);
}
