package com.intern.resource.base.mapper;

import com.intern.resource.base.dto.BookTypesDTO;
import com.intern.resource.base.entity.BookTypes;

public class BookTypesMapper {
    public static BookTypes dtoToEntity(final BookTypesDTO bookTypesDTO) {
        if (bookTypesDTO == null) {
            return null; // or handle as needed
        }
        return BookTypes.builder()
                .typesId(bookTypesDTO.getTypesId())
                .name(bookTypesDTO.getName())
                .typesCode(bookTypesDTO.getTypesCode())
                .build();
    }
    public static BookTypesDTO entityToDTO(final BookTypes bookTypes) {
        if (bookTypes == null) {
            return null; // or handle as needed
        }
        return BookTypesDTO.builder()
                .typesId(bookTypes.getTypesId())
                .name(bookTypes.getName())
                .typesCode(bookTypes.getTypesCode())
                .build();
    }
}