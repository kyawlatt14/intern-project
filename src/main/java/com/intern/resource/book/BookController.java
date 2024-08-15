package com.intern.resource.book;

import com.intern.resource.base.dto.BookDTO;
import com.intern.resource.base.entity.Book;
import com.intern.resource.base.repository.BookRepository;
import com.intern.resource.base.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "BOOKS-CONTROLLER")
public class BookController {
    private final BookService bookService;
    private final BookRepository bookRepository;

    @PostMapping("/save")
    public ResponseEntity<?> saveBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.saveBook(bookDTO));
    }
    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }
    @GetMapping("/allbook")
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }
    @PutMapping("/update/{bookId}")
    public BookDTO updateBook(@PathVariable Long bookId, @RequestBody BookDTO bookDTO) {
        return bookService.updateBook(bookId, bookDTO);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        // Clear the collection reference
        if (book.getCollection() != null) {
            book.setCollection(null);
        }
        if (book.getBookTypes()!= null) {
            book.setBookTypes(null);
        }
        bookRepository.save(book);
        bookService.deleteBook(id);
    }
}
