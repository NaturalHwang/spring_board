package beyond.board.author.service;

import beyond.board.author.domain.Author;
import beyond.board.author.dto.AuthorDetailDto;
import beyond.board.author.dto.AuthorReqDto;
import beyond.board.author.dto.AuthorResDto;
import beyond.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public void createMember(AuthorReqDto authorReqDto){
        Author author = authorReqDto.toEntity();
        if(authorReqDto.getPassword().length() < 8){
            throw new IllegalArgumentException("비밀번호는 8자리 이상이어야 합니다.");
        }
        authorRepository.save(author);
    }

    public AuthorDetailDto authorDetail(Long id){
        Optional<Author> optAuthor = authorRepository.findById(id);
        Author author = optAuthor.orElseThrow(()-> new EntityNotFoundException("없는 회원입니다."));

        return author.detFromEntity();
    }

    public List<AuthorResDto> authorList(){
        List<AuthorResDto> resDtos = new ArrayList<>();
        List<Author> authorList = authorRepository.findAll();
        for(Author author: authorList){
            resDtos.add(author.listFromEntity());
        }
        return resDtos;
    }
}
