package site.metacoding.junitproject.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import site.metacoding.junitproject.domain.Book;

@NoArgsConstructor
@Getter
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;

    public BookResponseDto toDto(Book bookPS) {
        this.id = bookPS.getId();
        this.title = bookPS.getTitle();
        this.author = bookPS.getAuthor();
        return this;
    }
}

