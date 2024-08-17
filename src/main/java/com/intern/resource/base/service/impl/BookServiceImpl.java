package com.intern.resource.base.service.impl;

import com.intern.resource.base.dto.BookDTO;
import com.intern.resource.base.dto.BookTypesDTO;
import com.intern.resource.base.dto.CollectionDTO;
import com.intern.resource.base.entity.Book;
import com.intern.resource.base.entity.BookTypes;
import com.intern.resource.base.entity.Collection;
import com.intern.resource.base.mapper.BookMapper;
import com.intern.resource.base.mapper.BookTypesMapper;
import com.intern.resource.base.mapper.CollectionMapper;
import com.intern.resource.base.repository.BookRepository;
import com.intern.resource.base.repository.BookTypesRepository;
import com.intern.resource.base.repository.CollectionRepository;
import com.intern.resource.base.service.BookService;
import com.intern.resource.base.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    final BookRepository bookRepository;
    private final CollectionRepository collectionRepository;
    private final BookTypesRepository bookTypesRepository;

    @Override
    @Transactional
    public BookDTO saveBook(BookDTO bookDTO) {
        //saveEntity
        final Book book = bookRepository.save(BookMapper.dtoToEntity(bookDTO));

        //search from database using bookDTO.getCollectionId()
        Collection collection = collectionRepository.findById(bookDTO.getCollectionId()).orElseThrow();
        collection.add(book);
        collectionRepository.save(collection);
        CollectionDTO collectionDTO = CollectionMapper.entityToDTO(collection);
        // ResponseDTO responseDTO = CollectionMapper.response(collection);

        //search from database using bookDTO.getBookTypesDTO()
        BookTypes bookTypes = bookTypesRepository.findById(bookDTO.getTypesId()).orElseThrow();
        bookTypes.add(book);
        bookTypesRepository.save(bookTypes);
        BookTypesDTO bookTypesDTO = BookTypesMapper.entityToDTO(bookTypes);

        //response Data
        BookDTO bookDTO1 = BookMapper.entityToDTO(book);
        bookDTO1.setCollectionDTO(collectionDTO);
        //  bookDTO1.setCollectionResponseDTO(responseDTO);
        bookDTO1.setBookTypesDTO(bookTypesDTO);

        return bookDTO1;
    }





    @Override
    @Transactional
    public BookDTO updateBook(Long bookId, BookDTO bookDTO) {
        Collection collection=collectionRepository.findById(bookDTO.getCollectionId()).orElseThrow();
        //  CollectionDTO collectionDTO = CollectionMapper.entityToDTO(collection);

        BookTypes bookTypes=bookTypesRepository.findById(bookDTO.getTypesId()).orElseThrow();
        //  BookTypesDTO bookTypesDTO = BookTypesMapper.entityToDTO(bookTypes);

        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        existingBook.setBookTypes(bookTypes);
        existingBook.setCollection(collection);
        existingBook.setMainTitle(bookDTO.getMainTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setPublisher(bookDTO.getPublisher());
        existingBook.setPublisherYear(ObjectUtils.isEmpty(bookDTO.getPublisherYear())
                ? DateUtils.getNowDate()
                : DateUtils.stringToLongDate(bookDTO.getPublisherYear()));
        existingBook.setSubtitle(bookDTO.getSubtitle());



        // Save the updated book back to the database
        Book updatedBook = bookRepository.save(existingBook);
        BookDTO bookDTO1 = BookMapper.entityToDTO(updatedBook);


        return bookDTO1;
    }

    @Override
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Clear the collection reference
        book.setDisable(false);
        bookRepository.save(book);

    }

    @Override
    @Transactional
    public List<BookDTO> getBooksByEnableStatus(boolean disable) {
        List<Book> books= bookRepository.findByDisable(disable);

        return books.stream()
                .map(BookMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDTO> getBookIfEnabled(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findByIdAndDisableTrue(bookId);
        return optionalBook.map(BookMapper::entityToDTO);
    }
}
