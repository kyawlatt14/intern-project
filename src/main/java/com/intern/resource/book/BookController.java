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
import java.util.Optional;

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
//    @GetMapping("/findbyid")
//    public BookDTO getBookById(@RequestParam Long id) {
//        return bookService.getBookById(id);
//    }
    //    @GetMapping("/allbook")
//    public List<BookDTO> getAllBooks() {
//        return bookService.getAllBooks();
//    }
    @PutMapping("/update")
    public BookDTO updateBook(@RequestParam Long bookId, @RequestBody BookDTO bookDTO) {
        return bookService.updateBook(bookId, bookDTO);
    }
    @DeleteMapping("/delete")
    public void deleteBook(@RequestParam Long id) {
        bookService.deleteBook(id);
    }
    @GetMapping("enable")
    public ResponseEntity<List<BookDTO>> getBooksByEnable(@RequestParam boolean disable) {
        List<BookDTO> books = bookService.getBooksByEnableStatus(disable);
        return ResponseEntity.ok(books);
    }

    @GetMapping("book-enable")
    public ResponseEntity<BookDTO> getBookIfEnabled(@RequestParam Long bookId) {
        Optional<BookDTO> bookDTO = bookService.getBookIfEnabled(bookId);
        return bookDTO.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
