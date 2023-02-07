package site.metacoding.junitproject.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Service;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSenderStub;
import site.metacoding.junitproject.web.dto.BookResponseDto;
import site.metacoding.junitproject.web.dto.BookSaveRequestDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
public class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void 책등록하기_테스트() {
        //given
        BookSaveRequestDto dto = new BookSaveRequestDto();
        dto.setTitle("junit강의");
        dto.setAuthor("메타코딩");

        //stub
        MailSenderStub mailSenderStub = new MailSenderStub();

        //when
        BookService bookService = new BookService(bookRepository, mailSenderStub);
        BookResponseDto bookResponseDto = bookService.책등록하기(dto);

        //then
        assertEquals(dto.getTitle(), bookResponseDto.getTitle());
        assertEquals(dto.getAuthor(), bookResponseDto.getAuthor());

    }

}
