package site.metacoding.junitproject.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.metacoding.junitproject.service.BookService;
import site.metacoding.junitproject.web.dto.response.BookListResponseDto;
import site.metacoding.junitproject.web.dto.response.BookResponseDto;
import site.metacoding.junitproject.web.dto.request.BookSaveRequestDto;
import site.metacoding.junitproject.web.dto.response.CommonResponseDto;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    //1. 책등록
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveRequestDto bookSaveRequestDto, BindingResult bindingResult) {

        //AOP처리하는 게 더 좋음
        if(bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage()); //field = title, author
            }
            System.out.println("=======================");
            System.out.println(errorMap.toString());
            System.out.println("=======================");

            throw new RuntimeException(errorMap.toString());
        }

        BookResponseDto bookResponseDto = bookService.책등록하기(bookSaveRequestDto);
        return new ResponseEntity<>(CommonResponseDto.builder().code(1).msg("글 저장 성공").body(bookResponseDto).build(), HttpStatus.CREATED); //=201
    }

    @PostMapping("/api/v2/book")
    public ResponseEntity<?> saveBookV2(@RequestBody BookSaveRequestDto bookSaveRequestDto) {


        BookResponseDto bookResponseDto = bookService.책등록하기(bookSaveRequestDto);
        return new ResponseEntity<>(CommonResponseDto.builder().code(1).msg("글 저장 성공").body(bookResponseDto).build(), HttpStatus.CREATED); //=201
    }

    //2. 책목록보기
    //List타입 반환 추천 X -> dto 통해 오브젝트 형태로

    @GetMapping("/api/v1/book")
    public ResponseEntity<?> getBookList() {
        BookListResponseDto bookListResponseDto =  bookService.책목록보기();
        return new ResponseEntity<>(CommonResponseDto.builder().code(1).msg("책 조회 성공").body(bookListResponseDto).build(), HttpStatus.OK); //200
    }

    //3. 책단일조회
    public ResponseEntity<?> getBookOne() {

        return null;
    }

    //4. 책 삭제하기
    public ResponseEntity<?> deleteBook() {

        return null;
    }

    //5. 책 수정하기
    public ResponseEntity<?> updateBook() {

        return null;
    }
}
