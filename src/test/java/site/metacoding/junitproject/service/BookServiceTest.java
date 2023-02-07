package site.metacoding.junitproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.web.dto.BookResponseDto;
import site.metacoding.junitproject.web.dto.BookSaveRequestDto;

import javax.transaction.Transactional;

@RequiredArgsConstructor //final 생성자 주입
@Service
public class BookServiceTest {

    private final BookRepository bookRepository;

    //1. 책 등록
    @Transactional(rollbackOn = Exception.class)
    public BookResponseDto 책등록하기(BookSaveRequestDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());
        return new BookResponseDto().toDto(bookPS);
    }

    //2. 책 목록

    //3. 책 단일 조회

    //4. 책 삭제

    //5. 책 수정
}
