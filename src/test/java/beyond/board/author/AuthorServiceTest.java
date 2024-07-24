package beyond.board.author;

import beyond.board.author.domain.Author;
import beyond.board.author.domain.Role;
import beyond.board.author.dto.AuthorSaveReqDto;
import beyond.board.author.dto.AuthorUpdateDto;
import beyond.board.author.repository.AuthorRepository;
import beyond.board.author.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorRepository authorRepository;

//    저장 및 detail 조회
    @Test
    void saveAndFind(){
        AuthorSaveReqDto saveDto = new AuthorSaveReqDto(
                "hong", "hong33@naver.com", "12341234", Role.ADMIN);
        Author author = authorService.authorCreate(saveDto);
        Author authorDetail = authorService.authorfindByEmail("hong@naver.com");
        Assertions.assertEquals(authorDetail.getEmail(), author.getEmail());
    }
//    update 검증
    @Test
    void updateTest(){
//        객체 create 저장
        AuthorSaveReqDto saveDto = new AuthorSaveReqDto(
                "hong", "hong22@naver.com", "12341234", Role.ADMIN);
        Author author = authorService.authorCreate(saveDto);
//        수정 작업(name, password)
        AuthorUpdateDto updateDto = new AuthorUpdateDto("hang", "43214321");
        authorService.updateAuthor(author.getId(), updateDto);

//        Author savedAuthor = authorService.authorfindByEmail("hong@naver.com");
//        ID로 조회하는 것이 더 정확하다(email이 unique가 아니거나 비어있거나 하는 경우).
        Author savedAuthor = authorRepository.findById(author.getId()).orElseThrow(
//                서비스를 검증하는데 repo를 가져오는 것은 적절하지 않음(레포 자체에 문제가 있을 수 있다)
//                -> MockTest 하는 이유
                () -> new EntityNotFoundException("없는 회원입니다")
        );
//        수정 후 재조회: name, password가 맞는지 각각 검증
        Assertions.assertEquals("hang", savedAuthor.getName());
        Assertions.assertEquals("43214321", savedAuthor.getPassword());
    }
//    findAll 검증
    @Test
    public void findAllTest(){
        int size = authorService.authorList().size();
//        3개 author 객체 저장
        AuthorSaveReqDto saveDto1 = new AuthorSaveReqDto(
                "hong", "hong1@naver.com", "12341234", Role.ADMIN);
        authorService.authorCreate(saveDto1);
        AuthorSaveReqDto saveDto2 = new AuthorSaveReqDto(
                "hong", "hong2@naver.com", "12341234", Role.ADMIN);
        authorService.authorCreate(saveDto2);
        AuthorSaveReqDto saveDto3 = new AuthorSaveReqDto(
                "hong", "hong3@naver.com", "12341234", Role.ADMIN);
        authorService.authorCreate(saveDto3);
//        authorList를 조회한 후 저장한 개수와 저장된 개수 비교
        Assertions.assertEquals(size+3, authorService.authorList().size());
    }
}
