package com.intern.resource.base.repository;

import com.intern.resource.base.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b JOIN FETCH b.collection WHERE b.id = :id")
    Book findBookWithCollectionById(@Param("id") Long id);
}
