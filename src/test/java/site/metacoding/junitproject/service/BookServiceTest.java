package site.metacoding.junitproject.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Service;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.util.MailSenderStub;
import site.metacoding.junitproject.web.dto.BookResponseDto;
import site.metacoding.junitproject.web.dto.BookSaveRequestDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class) //가짜 메모리 환경
public class BookServiceTest {

    @InjectMocks //@Mock 환경을 @InjectMocks를 통해 넣어줌
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    @Test
    public void 책등록하기_테스트() {
        //given
        BookSaveRequestDto dto = new BookSaveRequestDto();
        dto.setTitle("junit강의");
        dto.setAuthor("메타코딩");

        //stub 행동 정의(=가설)
        lenient().when(bookRepository.save(any())).thenReturn(dto.toEntity());
        lenient().when(mailSender.send()).thenReturn(true);

        //when
        BookResponseDto bookResponseDto = bookService.책등록하기(dto);

        //then
//        assertEquals(dto.getTitle(), bookResponseDto.getTitle());
//        assertEquals(dto.getAuthor(), bookResponseDto.getAuthor());
        //asertj 사용
        assertThat(dto.getTitle()).isEqualTo(bookResponseDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookResponseDto.getAuthor());


    }

}
