package com.intern.resource.base.repository;

import com.intern.resource.base.entity.BookTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookTypesRepository extends JpaRepository<BookTypes, Long> {
    boolean existsByName(String name);
    List<BookTypes> findByDisable(@Param("disable") boolean disable);
    Optional<BookTypes> findByIdAndDisableTrue(Long id);
}
