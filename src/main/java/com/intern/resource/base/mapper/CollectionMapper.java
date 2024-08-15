package com.intern.resource.base.mapper;

import com.intern.resource.base.dto.CollectionDTO;
import com.intern.resource.base.dto.ResponseDTO;
import com.intern.resource.base.entity.Collection;
import com.intern.resource.base.util.DateUtils;
import org.springframework.util.ObjectUtils;

public class CollectionMapper {
    public static Collection dtoToEntity(final CollectionDTO collectionDTO) {
        return Collection.builder()
                .id(collectionDTO.getId())
                .name(collectionDTO.getName())
                .createDate(ObjectUtils.isEmpty(collectionDTO.getCreateDate())
                        ? DateUtils.getNowDate()
                        : DateUtils.stringToLongDate(collectionDTO.getCreateDate()))
                .build();
    }

    public static CollectionDTO entityToDTO(final Collection collection) {
        return CollectionDTO.builder()
                .id(collection.getId())
                .name(collection.getName())
                .createDate(DateUtils.longToStringDate(collection.getCreateDate()))
                .build();
    }

    public static ResponseDTO response(Collection collection) {
        return ResponseDTO.builder()
                .name(collection.getName())
                .build();
    }
}
