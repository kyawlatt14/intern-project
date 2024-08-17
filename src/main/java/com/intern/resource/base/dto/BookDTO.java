package com.intern.resource.base.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intern.resource.base.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String mainTitle;
    private String subtitle;
    private String author;
    private String publisher;
    private String  publisherYear;

    private Long  collectionId;
    private CollectionDTO collectionDTO;

    //  private ResponseDTO collectionResponseDTO;

    private Long typesId;
    private BookTypesDTO bookTypesDTO;
    @JsonIgnore
    private String collectionName;
    @JsonIgnore
    private List<Book> books;
    private boolean disable;
}
