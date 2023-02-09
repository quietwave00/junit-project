package site.metacoding.junitproject.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.web.dto.response.BookListResponseDto;
import site.metacoding.junitproject.web.dto.response.BookResponseDto;
import site.metacoding.junitproject.web.dto.request.BookSaveRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.assertj.core.api.Assertions.*;


@ActiveProfiles("dev") //dev모드일 때만 실행
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
        assertThat(bookResponseDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookResponseDto.getAuthor()).isEqualTo(dto.getAuthor());
    }

    @Test
    public void 책목록보기_테스트() {
        //given


        //stub
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "junit강의", "메타코딩"));
        books.add(new Book(2L, "spring강의", "겟인데어"));
        lenient().when(bookRepository.findAll()).thenReturn(books);

        //when
        BookListResponseDto bookListResponseDto = bookService.책목록보기();

        //then
        assertThat(bookListResponseDto.getItems().get(0).getTitle()).isEqualTo("junit강의");
        assertThat(bookListResponseDto.getItems().get(0).getAuthor()).isEqualTo("메타코딩");
        assertThat(bookListResponseDto.getItems().get(1).getTitle()).isEqualTo("spring강의");
        assertThat(bookListResponseDto.getItems().get(1).getAuthor()).isEqualTo("겟인데어");


    }

    @Test
    public void 책단일조회_테스트() {
        //given
        Long id = 1L;

        //stub
        Book book = new Book(1L, "junit강의", "메타코딩");
        Optional<Book> bookOP = Optional.of(book);
        lenient().when(bookRepository.findById(id)).thenReturn(bookOP);

        //when
        BookResponseDto bookResponseDto = bookService.책단일조회(id);

        //then
        assertThat(bookResponseDto.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookResponseDto.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    public void 책수정하기_테스트() {
        //given
        Long id = 1L;
        BookSaveRequestDto dto = new BookSaveRequestDto();
        dto.setTitle("spring강의");
        dto.setAuthor("겟인데어");

        //stub
        Book book = new Book(1L, "junit강의", "메타코딩");
        Optional<Book> bookOP = Optional.of(book);
        lenient().when(bookRepository.findById(id)).thenReturn(bookOP);

        //when
        BookResponseDto bookResponseDto = bookService.책수정하기(id, dto);

        //then
        assertThat(bookResponseDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookResponseDto.getAuthor()).isEqualTo(dto.getAuthor());

    }

}
