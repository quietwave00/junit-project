package site.metacoding.junitproject.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

//Controller(3)(클라이언트) - Service(2)(기능) - Repository(1)(DB)

//method는 실행 순서가 보장이 안 됨(@Order 사용해야 함)
//테스트 메서드 하나 싫행 후 종료되면 데이터 초기화 됨(@Transactional) -> 그런데 auto_increment값이 초기화가 안 됨
//table 드랍시켜 주는 sql파일 추가


@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩(단위 테스트)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

//    @BeforeAll //테스트 시작 전 한 번만 실행
    @BeforeEach //테스트 시작 전 한 번씩 실행
    public void 데이터준비() {
        String title = "junit";
        String author = "겟인데어";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);
    } //트랜잭션 바로 종료 X
    //데이터준비() + 책등록(), 데이터준비() + 책목록보기(), ... -> insert된 전체 데이터 보면 size = 1


    // 1. 책 등록
    @Test
    public void 책등록_test() {
        // given(데이터 준비)
        String title = "junit5";
        String author = "메타코딩";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when(테스트 실행)
        Book bookPS = bookRepository.save(book); // DB에 저장, pk 생성
                                                  // save 메서드가 DB에 저장된 Book을 return(DB와 동기화된 데이터 -> 영속화됨)

        // then(검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    } //트랜잭션 종료(저장된 데이터 초기화)

    // 2. 책 목록
    @Test
    public void 책목록보기_test() {
        //given
        String title = "junit";
        String author = "겟인데어";

        //when
        List<Book> booksPS = bookRepository.findAll();
        System.out.println("------------- size:" + booksPS.size() + " -------------");

        //then
        assertEquals(title, booksPS.get(0).getTitle());
        assertEquals(author, booksPS.get(0).getAuthor());
    }

    // 3. 책 단일 조회
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책단일조회_test() {
        //given
        String title = "junit";
        String author = "겟인데어";

        //when
        Book bookPS = bookRepository.findById(1L).get();

        //then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());

    }

    // 4. 책 수정


    // 5. 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책삭제_test() {
        //given
        Long id = 1L;

        //when
        bookRepository.deleteById(id); //실패하면 exception

        //then
//        Optional<Book> bookPS = bookRepository.findById(id); //null일 가능성 O -> Optional 사용
        assertFalse(bookRepository.findById(id).isPresent()); //삭제했으니까 isPresent가 false여야 함
    }

}
