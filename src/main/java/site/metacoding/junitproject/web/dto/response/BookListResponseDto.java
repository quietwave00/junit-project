package site.metacoding.junitproject.web.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BookListResponseDto {
    //JSON 형태로
    List<BookResponseDto> items;

    @Builder
    public BookListResponseDto(List<BookResponseDto> bookList) {
        this.items = bookList;
    }
}
