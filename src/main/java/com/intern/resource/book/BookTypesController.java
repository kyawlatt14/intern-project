package com.intern.resource.book;

import com.intern.resource.base.dto.BookTypesDTO;
import com.intern.resource.base.repository.BookRepository;
import com.intern.resource.base.repository.BookTypesRepository;
import com.intern.resource.base.service.BookTypesService;
import com.intern.resource.base.entity.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bookTypes")
@RequiredArgsConstructor
@Tag(name = "BOOKTYPES-CONTROLLER")
public class BookTypesController {
    private final BookTypesService bookTypesService;
    private final BookTypesRepository bookTypesRepository;
    private final BookRepository bookRepository;

    @PostMapping
    public ResponseEntity<?> saveBookTypes(@RequestBody BookTypesDTO bookTypesDTO) {
        return ResponseEntity.ok(bookTypesService.saveBookTypes(bookTypesDTO));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBookTypes(@RequestParam Long bookTypesId) {
        Optional<BookTypes> bookTypes=bookTypesRepository.findById(bookTypesId);
        if (bookTypes.isPresent()) {
            BookTypes bookTypes1=bookTypes.get();
            List<Book> books=bookTypes1.getBooks();
            for (Book book : books) {
                book.setBookTypes(null);
                bookRepository.save(book);
            }
        }
        return ResponseEntity.ok(bookTypesService.delete(bookTypesId));
    }
}