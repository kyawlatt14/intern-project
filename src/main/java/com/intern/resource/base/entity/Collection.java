package com.intern.resource.base.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "collection")
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String name;
    private long createDate;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "collection", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Book> books;
    public void add(Book tempBook){
        if(this.books == null)
            books = new ArrayList<>();
        books.add(tempBook);
        tempBook.setCollection(this);
    }
}