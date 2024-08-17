package com.intern.resource.base.repository;

import com.intern.resource.base.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByDisable(@Param("disable") boolean disable);
    Optional<Book> findByIdAndDisableTrue(Long bookid );


}
