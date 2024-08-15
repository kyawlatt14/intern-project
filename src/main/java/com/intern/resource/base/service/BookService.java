package com.intern.resource.base.service;

import com.intern.resource.base.dto.BookDTO;

import java.util.List;

public interface BookService {
    BookDTO saveBook(BookDTO bookDTO);
    BookDTO getBookById(Long id);
    List<BookDTO> getAllBooks();
    BookDTO updateBook(Long bookId,BookDTO bookDTO);
    void deleteBook(Long bookId);
}
