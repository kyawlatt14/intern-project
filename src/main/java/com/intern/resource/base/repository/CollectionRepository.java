package com.intern.resource.base.repository;

import com.intern.resource.base.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection,Long> {
    boolean existsByName(String name);
}