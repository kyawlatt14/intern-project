package com.intern.resource.base.mapper;

import com.intern.resource.base.dto.BookTypesDTO;
import com.intern.resource.base.entity.BookTypes;

public class BookTypesMapper {
    public static BookTypes dtoToEntity(final BookTypesDTO bookTypesDTO) {
        if (bookTypesDTO == null) {
            return null; // or handle as needed
        }
        return BookTypes.builder()
                .id(bookTypesDTO.getId())
                .name(bookTypesDTO.getName())
                .typesCode(bookTypesDTO.getTypesCode())
                .disable(bookTypesDTO.isDisable())
                .build();
    }
    public static BookTypesDTO entityToDTO(final BookTypes bookTypes) {
        if (bookTypes == null) {
            return null; // or handle as needed
        }
        return BookTypesDTO.builder()
                .id(bookTypes.getId())
                .name(bookTypes.getName())
                .typesCode(bookTypes.getTypesCode())
                . disable(bookTypes.isDisable())
                .build();
    }
}