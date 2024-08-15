package com.intern.resource.base.service;

import com.intern.resource.base.dto.BookTypesDTO;

public interface BookTypesService {
    BookTypesDTO saveBookTypes(BookTypesDTO bookTypesDTO);
    String delete(Long bookTypesId);
}
