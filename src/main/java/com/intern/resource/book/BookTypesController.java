package com.intern.resource.book;

import com.intern.resource.base.dto.BookDTO;
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


    @PostMapping
    public ResponseEntity<?> saveBookTypes(@RequestBody BookTypesDTO bookTypesDTO) {
        return ResponseEntity.ok(bookTypesService.saveBookTypes(bookTypesDTO));
    }

    @DeleteMapping("/delete")
    public void deleteBook(@RequestParam Long id) {
        bookTypesService.deleteBookTypes(id);
    }
    @GetMapping("enable-types")
    public ResponseEntity<List<BookTypesDTO>> getBookTypesByEnable(@RequestParam boolean disable) {
        List<BookTypesDTO> books = bookTypesService.getBookTypesByEnableStatus(disable);
        return ResponseEntity.ok(books);
    }

    @GetMapping("bookTypes-enable")
    public ResponseEntity<BookTypesDTO> getBookTypesIfEnabled(@RequestParam Long id) {
        Optional<BookTypesDTO> bookTypesDTO = bookTypesService.getBookTypesIfEnabled(id);
        return bookTypesDTO.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}