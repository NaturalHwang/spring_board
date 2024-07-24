package beyond.board.author.service;

import beyond.board.author.domain.Author;
import beyond.board.author.dto.AuthorDetailDto;
import beyond.board.author.dto.AuthorListResDto;
import beyond.board.author.dto.AuthorSaveReqDto;
import beyond.board.author.dto.AuthorUpdateDto;
import beyond.board.author.repository.AuthorRepository;
import beyond.board.post.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
// 조회 작업시에 readOnly 설정하면 성능 향상
// 다만, 저장 작업시에는 Transactional 필요
@Transactional(readOnly = true)
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    @Transactional
//    public void authorCreate(AuthorSaveReqDto authorSaveReqDto){ // TDD를 위한 주석 처리
    public Author authorCreate(AuthorSaveReqDto authorSaveReqDto){
        Author author = authorSaveReqDto.toEntity();
        if(authorSaveReqDto.getPassword().length() < 8){
            throw new IllegalArgumentException("비밀번호는 8자리 이상이어야 합니다.");
        }
        if(authorRepository.findByEmail(authorSaveReqDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
//        cascade persist 테스트. remove 테스트는 회원 삭제로 대체
        author.getPosts().add(Post.builder()
                                    .title("가입인사")
                                    .contents("안녕하세요. " + author.getName() + "입니다.")
                                    .author(author)
                                    .appointment("N")
                                    .build());
//        authorRepository.save(author); // TDD를 위한 주석 처리
        return authorRepository.save(author);
    }

    public AuthorDetailDto authorDetail(Long id){
//        Optional<Author> optAuthor = authorRepository.findById(id);
//        Author author = optAuthor.orElseThrow(()-> new EntityNotFoundException("없는 회원입니다."));
        Author author = authorRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("없는 회원입니다."));
        AuthorDetailDto authorDetailDto = new AuthorDetailDto();
        return authorDetailDto.fromEntityToDto(author);
//        return author.detFromEntity();
    }

    public List<AuthorListResDto> authorList(){
        List<AuthorListResDto> resDtos = new ArrayList<>();
        List<Author> authorList = authorRepository.findAll();
        for(Author author: authorList){
            resDtos.add(author.listFromEntity());
        }
        return resDtos;
    }

    public Author authorfindByEmail(String email){
        Author author = authorRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 이메일입니다"));
        return author;
    }

    @Transactional
    public void deleteAuthor(Long id){
        authorRepository.deleteById(id);
    }

    @Transactional
    public void updateAuthor(Long id, AuthorUpdateDto authorUpdateDto){
        Author author = authorRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("존재하지 않는 유저입니다."));
        author.updateAuthor(authorUpdateDto);
//        JPA가 특정 엔티티에 변경을 자동으로 인지하고 변경사항을 DB에 반영하는 것이 dirty checking(변경 감지)
//        따라서 이 경우엔 save가 필수가 아님.
//        더티체킹시 Transaction 어노테이션 필수
//        authorRepository.save(author);
    }
}
