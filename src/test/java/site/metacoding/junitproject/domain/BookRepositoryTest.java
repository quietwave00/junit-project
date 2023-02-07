package site.metacoding.junitproject.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩(단위 테스트)
public class BookRepositoryTest {
    //Controller(3)(클라이언트) - Service(2)(기능) - Repository(1)(DB)

    @Autowired
    private BookRepository bookRepository;

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
    }



    // 2. 책 목록

    // 3. 책 단일 조회

    // 4. 책 수정

    // 5. 책 삭제

}
