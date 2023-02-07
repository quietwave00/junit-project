package site.metacoding.junitproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.web.dto.BookResponseDto;
import site.metacoding.junitproject.web.dto.BookSaveRequestDto;

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
        return new BookResponseDto().toDto(bookPS);
    }

    //2. 책 목록
    public List<BookResponseDto> 책목록보기() {
        return bookRepository.findAll().stream()
                .map(new BookResponseDto()::toDto)
                .collect(Collectors.toList());
    }

    //3. 책 단일 조회
    public BookResponseDto 책단일조회(Long id) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()) { //값 존재
            return new BookResponseDto().toDto(bookOP.get());
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
    public void 책수정하기(Long id, BookSaveRequestDto dto) { //id로 book을 찾고 update 처리
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()) { //값이 있을 경우
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
        } else { //null
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    } //더티체킹(flush)


}
