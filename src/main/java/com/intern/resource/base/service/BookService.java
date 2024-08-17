package com.intern.resource.base.service;

import com.intern.resource.base.dto.BookDTO;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookDTO saveBook(BookDTO bookDTO);
    BookDTO updateBook(Long bookId,BookDTO bookDTO);
    void deleteBook(Long bookId);
    List<BookDTO> getBooksByEnableStatus(boolean disable);
    Optional<BookDTO> getBookIfEnabled(Long id);
}
