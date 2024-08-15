package com.intern.resource.base.service;

import com.intern.resource.base.dto.CollectionDTO;

public interface CollectionService {
    CollectionDTO saveCollection(CollectionDTO collectionDTO);
    String delete(Long collectionId);
}
