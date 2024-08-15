package com.intern.resource.book;

import com.intern.resource.base.dto.CollectionDTO;
import com.intern.resource.base.entity.Book;
import com.intern.resource.base.entity.Collection;
import com.intern.resource.base.repository.BookRepository;
import com.intern.resource.base.repository.CollectionRepository;
import com.intern.resource.base.service.CollectionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/collections")
@RequiredArgsConstructor
@Tag(name = "COLLECTIONS-CONTROLLER")
public class CollectionController {
    private final CollectionService collectionService;
    private final CollectionRepository collectionRepository;
    private final BookRepository bookRepository;

    @PostMapping
    public ResponseEntity<?> saveCollection(@RequestBody CollectionDTO collectionDTO) {
        return ResponseEntity.ok(collectionService.saveCollection(collectionDTO));
    }
    @DeleteMapping
    public ResponseEntity<?> deleteCollection(@RequestParam Long collectionId) {
        Optional<Collection> collection=collectionRepository.findById(collectionId);

        if (collection.isPresent()) {
            Collection collection1=collection.get();
            List<Book> books=collection1.getBooks();

            for (Book book : books) {
                book.setCollection(null);
                bookRepository.save(book);
            }

        }
        return ResponseEntity.ok(collectionService.delete(collectionId));
    }

}
