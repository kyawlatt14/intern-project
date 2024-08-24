package com.intern.resource.base.mapper;

import com.intern.resource.base.dto.BookDTO;
import com.intern.resource.base.entity.Book;
import com.intern.resource.base.util.DateUtils;
import org.springframework.util.ObjectUtils;

public class BookMapper {
    public static Book dtoToEntity(final BookDTO bookDTO) {
        Book.BookBuilder bookBuilder = Book.builder()
                .id(bookDTO.getId())
                .author(bookDTO.getAuthor())
                .mainTitle(bookDTO.getMainTitle())
                .publisher(bookDTO.getPublisher())
                .publisherYear(ObjectUtils.isEmpty(bookDTO.getPublisherYear())
                        ? DateUtils.getNowDate()
                        : DateUtils.stringToLongDate(bookDTO.getPublisherYear()))
                .subtitle(bookDTO.getSubtitle())
                .disable(bookDTO.isDisable());
        return bookBuilder.build();
    }

    public static BookDTO entityToDTO(final Book book) {
        BookDTO.BookDTOBuilder bookDTOBuilder = BookDTO.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .mainTitle(book.getMainTitle())
                .publisher(book.getPublisher())
                .collectionDTO(CollectionMapper.entityToDTO(book.getCollection()))
                .bookTypesDTO(BookTypesMapper.entityToDTO(book.getBookTypes()))
                .publisherYear(ObjectUtils.isEmpty(book.getPublisherYear())
                        ? String.valueOf(DateUtils.getNowDate())
                        : DateUtils.longToStringDate(book.getPublisherYear()))
                .subtitle(book.getSubtitle())
                .disable(book.isDisable());
        return bookDTOBuilder.build();
    }
}
