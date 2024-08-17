package com.intern.resource.base.service.impl;

import com.intern.resource.base.dto.BookTypesDTO;
import com.intern.resource.base.entity.Book;
import com.intern.resource.base.entity.BookTypes;
import com.intern.resource.base.mapper.BookMapper;
import com.intern.resource.base.mapper.BookTypesMapper;
import com.intern.resource.base.repository.BookTypesRepository;
import com.intern.resource.base.service.BookTypesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookTypesServiceImpl implements BookTypesService {
    private final BookTypesRepository bookTypesRepository;

    @Override
    public BookTypesDTO saveBookTypes(BookTypesDTO bookTypesDTO) {
        if (bookTypesRepository.existsByName(bookTypesDTO.getName())) {
            throw new RuntimeException("BookTypes already exists");
        }
        final BookTypes bookTypes = bookTypesRepository.save(BookTypesMapper.dtoToEntity(bookTypesDTO));
        return BookTypesMapper.entityToDTO(bookTypes);
    }

    @Override
    public void deleteBookTypes(Long typesId) {
        BookTypes bookTypes = bookTypesRepository.findById(typesId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Clear the collection reference
        bookTypes.setDisable(false);
        bookTypesRepository.save(bookTypes);
    }

    @Override
    public List<BookTypesDTO> getBookTypesByEnableStatus(boolean disable) {
        List<BookTypes> bookTypes= bookTypesRepository.findByDisable(disable);
        return bookTypes.stream()
                .map(BookTypesMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookTypesDTO> getBookTypesIfEnabled(Long id) {
        Optional<BookTypes> optionalBookTypes = bookTypesRepository.findByIdAndDisableTrue(id);
        return optionalBookTypes.map(BookTypesMapper::entityToDTO);
    }


}
