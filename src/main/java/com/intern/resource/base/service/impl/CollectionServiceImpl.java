package com.intern.resource.base.service.impl;

import com.intern.resource.base.dto.CollectionDTO;
import com.intern.resource.base.entity.Collection;
import com.intern.resource.base.mapper.CollectionMapper;
import com.intern.resource.base.repository.CollectionRepository;
import com.intern.resource.base.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;
    @Override
    public  CollectionDTO saveCollection(CollectionDTO collectionDTO) {
        if (collectionRepository.existsByName(collectionDTO.getName())){
            throw new RuntimeException("Collection already exists");
        }
        final Collection collection=   collectionRepository.save(CollectionMapper.dtoToEntity(collectionDTO));
        return CollectionMapper.entityToDTO(collection);
    }

    @Override
    public String delete(Long collectionId) {
        collectionRepository.deleteById(collectionId);
        return collectionRepository.existsById(collectionId)?"Fail!":"Success";
    }

}
