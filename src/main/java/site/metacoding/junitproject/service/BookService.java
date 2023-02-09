package site.metacoding.junitproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.web.dto.response.BookListResponseDto;
import site.metacoding.junitproject.web.dto.response.BookResponseDto;
import site.metacoding.junitproject.web.dto.request.BookSaveRequestDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor //final 생성자 주입
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final MailSender mailSender;

    //1. 책 등록
    @Transactional(rollbackOn = Exception.class)
    public BookResponseDto 책등록하기(BookSaveRequestDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());
        return bookPS.toDto();
    }

    //2. 책 목록
    public BookListResponseDto 책목록보기() {
        List<BookResponseDto> dtos =  bookRepository.findAll().stream()
//                .map(new BookResponseDto()::toDto) //new는 한 번 되고 toDto()가 반복
//                .map((bookPS) -> new BookResponseDto().toDto(bookPS)) //고친 코드
                //refactor
                .map(Book::toDto)
                .collect(Collectors.toList());
        BookListResponseDto bookListResponseDto = BookListResponseDto.builder().bookList(dtos).build();

        return bookListResponseDto;
    }

    //3. 책 단일 조회
    public BookResponseDto 책단일조회(Long id) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()) { //값 존재
            Book bookPS = bookOP.get();
            return bookPS.toDto();
        } else { //null
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }


    //4. 책 삭제
    @Transactional(rollbackOn = RuntimeException.class)
    public void 책삭제하기(Long id) {
        bookRepository.deleteById(id);
    }

    //5. 책 수정
    @Transactional(rollbackOn = RuntimeException.class)
    public BookResponseDto 책수정하기(Long id, BookSaveRequestDto dto) { //id로 book을 찾고 update 처리
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()) { //값이 있을 경우
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
            return bookPS.toDto();
        } else { //null
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }

    } //더티체킹(flush)


}
