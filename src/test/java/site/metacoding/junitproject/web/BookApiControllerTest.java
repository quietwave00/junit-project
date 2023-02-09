package site.metacoding.junitproject.web;

import static org.assertj.core.api.Assertions.assertThat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.service.BookService;
import site.metacoding.junitproject.web.dto.request.BookSaveRequestDto;

//통합테스트(Controller, Service, Repository)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {



    @Autowired
    private TestRestTemplate restTemplate;

    private static ObjectMapper objectMapper; //convert

    private static HttpHeaders httpHeaders;

    @BeforeAll
    public static void init() {
        objectMapper = new ObjectMapper();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    //책 저장
    @Test
    public void saveBook_test() throws Exception {
        //given
        BookSaveRequestDto bookSaveRequestDto = new BookSaveRequestDto();
        bookSaveRequestDto.setTitle("스프링1강");
        bookSaveRequestDto.setAuthor("메타코딩"); // -> JSON형태로 와야 함

        String body = objectMapper.writeValueAsString(bookSaveRequestDto);

        //when
        HttpEntity<String> request = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange("/api/v1/book", HttpMethod.POST, request, String.class);
        System.out.println("=========================");
        System.out.println(response.getBody());

        //then
        //검증
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        String title = documentContext.read("$.body.title");
        String author = documentContext.read("$.body.author");

        assertThat(title).isEqualTo("스프링1강");
        assertThat(author).isEqualTo("메타코딩");
    }
}
