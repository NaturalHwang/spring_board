package beyond.board.author;

import beyond.board.author.domain.Author;
import beyond.board.author.domain.Role;
import beyond.board.author.dto.AuthorDetailDto;
import beyond.board.author.dto.AuthorSaveReqDto;
import beyond.board.author.repository.AuthorRepository;
import beyond.board.author.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@SpringBootTest
@Transactional
public class AuthorServiceMockTest {
    @Autowired
    private AuthorService authorService;
//    @Autowired
//    private AuthorRepository authorRepository;
//    가짜 객체를 만드는 작업을 목킹이라고 한다.
    @MockBean
    private AuthorRepository authorRepository;
//    => 가짜 repository 객체를 만들어 일관된 응답을 받을 수 있도록

    @Test
    public void findAuthorDetailTest(){
        AuthorSaveReqDto author = new AuthorSaveReqDto("test1", "test1@google.com", "12341234", Role.ADMIN);
        Author author1 = authorService.authorCreate(author);
//        Author mockAuthor = Author.builder().id(1L).name("test").email("test@naver.com").build();
        AuthorDetailDto authorDetailDto = authorService.authorDetail(author1.getId());
//        Author savedAuthor = authorRepository.findById(author1.getId()).orElseThrow(()->new EntityNotFoundException(""));
        Mockito.when(authorRepository.findById(author1.getId())).thenReturn(Optional.of(author1));
        Author savedAuthor = authorRepository.findById(author1.getId()).orElseThrow(() -> new EntityNotFoundException(""));
        Assertions.assertEquals(authorDetailDto.getEmail(), savedAuthor.getEmail());
    }
//    저장 및 detail 조회

//    update 검증

//    findAll 검증
}
