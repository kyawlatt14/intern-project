package com.intern.resource.base.service;

import com.intern.resource.base.dto.BookTypesDTO;

import java.util.List;
import java.util.Optional;

public interface BookTypesService {
    BookTypesDTO saveBookTypes(BookTypesDTO bookTypesDTO);
    void deleteBookTypes(Long typesId);

    List<BookTypesDTO> getBookTypesByEnableStatus(boolean disable);
    Optional<BookTypesDTO> getBookTypesIfEnabled(Long id);
}
