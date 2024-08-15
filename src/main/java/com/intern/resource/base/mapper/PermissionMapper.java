package com.intern.resource.base.mapper;

import com.intern.resource.base.entity.Permission;
import com.intern.resource.base.dto.PermissionDTO;
import com.intern.resource.base.util.DateUtils;
import org.springframework.util.ObjectUtils;

public class PermissionMapper {

    public static Permission dtoToEntity(PermissionDTO dto) {
        return Permission.builder()
                .name(dto.getName().toUpperCase())
                .description(dto.getDescription())
                .form(dto.getForm())
                .createdDate(ObjectUtils.isEmpty(dto.getCreateDate())
                        ? DateUtils.getNowDate()
                        :DateUtils.stringToLongDate(dto.getCreateDate()))
                .build();
    }

    public static PermissionDTO entityToDto(Permission entity) {
        return PermissionDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .form(entity.getForm())
                .createDate(DateUtils.longToStringDate(entity.getCreatedDate()))
                .build();
    }
}