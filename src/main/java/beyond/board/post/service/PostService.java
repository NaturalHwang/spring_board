package beyond.board.post.service;

import beyond.board.author.domain.Author;
import beyond.board.author.repository.AuthorRepository;
import beyond.board.post.domain.Post;
import beyond.board.post.dto.PostDetailDto;
import beyond.board.post.dto.PostReqDto;
import beyond.board.post.dto.PostResDto;
import beyond.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    @Autowired // 생략 가능
    public PostService(PostRepository postRepository, AuthorRepository authorRepository){
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public Post createPost(PostReqDto reqDto){
        Author author = authorRepository.findByEmail(reqDto.getEmail())
                        .orElseThrow(() -> new EntityNotFoundException("회원만 글을 작성할 수 있습니다."));
        Post post = reqDto.toEntity(author);
        return postRepository.save(post);
    }

    public PostDetailDto postDetail(Long id){
        Optional<Post> optPost = postRepository.findById(id);
        Post post = optPost.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다"));

        return post.detFromEntity();
    }

    public List<PostResDto> postList(){
        List<Post> postList = postRepository.findAll();
        List<PostResDto> resDtos = new ArrayList<>();

        for(Post post : postList){
            resDtos.add(post.listFromEntity());
            post.getAuthor().getEmail();
        }

        return resDtos;
    }
}
