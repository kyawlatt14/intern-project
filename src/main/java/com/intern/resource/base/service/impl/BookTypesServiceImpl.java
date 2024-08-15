package com.intern.resource.base.service.impl;

import com.intern.resource.base.dto.BookTypesDTO;
import com.intern.resource.base.entity.BookTypes;
import com.intern.resource.base.mapper.BookTypesMapper;
import com.intern.resource.base.repository.BookTypesRepository;
import com.intern.resource.base.service.BookTypesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public String delete(Long bookTypesId) {
        bookTypesRepository.deleteById(bookTypesId);
        return bookTypesRepository.existsById(bookTypesId) ?"Fail!":"Success";
    }
}
