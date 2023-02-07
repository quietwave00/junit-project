package site.metacoding.junitproject.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩(단위 테스트)
public class BookRepositoryTest {
    //Controller(3)(클라이언트) - Service(2)(기능) - Repository(1)(DB)

    @Autowired
    private BookRepository bookRepository;

    // 1. 책 등록
    @Test
    public void 책등록_test() {
        System.out.println("책등록_test 실행");
    }


    // 2. 책 목록

    // 3. 책 단일 조회

    // 4. 책 수정

    // 5. 책 삭제

}
