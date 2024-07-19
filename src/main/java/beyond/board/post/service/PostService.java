package beyond.board.post.service;

import beyond.board.author.domain.Author;
import beyond.board.author.service.AuthorService;
import beyond.board.post.domain.Post;
import beyond.board.post.dto.PostDetResDto;
import beyond.board.post.dto.PostListResDto;
import beyond.board.post.dto.PostSaveReqDto;
import beyond.board.post.dto.PostUpdateDto;
import beyond.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
//    private final AuthorRepository authorRepository;
//    복잡하지 않은 코드거나 순환 참조가 발생할 경우 repository autowired
//    코드가 복잡해질 경우 service에서 공통으로 구현하여 가져오는 것이 좋음
//    하지만 순환 참조에 빠질 가능성이 있어 설계 단계에서 충분히 고려해야됨.
//    (authorService가 postService를 참조하는 경우)
    private final AuthorService authorService;

    @Autowired
    public PostService(PostRepository postRepository, AuthorService authorService /*, AuthorRepository authorRepository*/){
        this.postRepository = postRepository;
        this.authorService = authorService;
//        this.authorRepository = authorRepository;
    }

//    author service에서 author 객체를 찾아 post의 toEntity에 넘기고,
//    jpa가 author 객체에서 author_id를 찾아 db에는 author_id가 들어감.
    public Post postCreate(PostSaveReqDto reqDto){
//        Author author = authorRepository.findByEmail(reqDto.getEmail())
        Author author = authorService.authorfindByEmail(reqDto.getEmail());
//        Post post = reqDto.toEntity(author);
//        return postRepository.save(post);
        Post post = postRepository.save(reqDto.toEntity(author));
        return post;
    }

    public PostDetResDto postDetail(Long id){
//        Optional<Post> optPost = postRepository.findById(id);
//        Post post = optPost.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다"));
        Post post = postRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("존재하지 않는 게시물입니다."));
        return post.detFromEntity();
    }

    public List<PostListResDto> postList(){
        List<Post> postList = postRepository.findAllFetch();
        List<PostListResDto> resDtos = new ArrayList<>();

        for(Post post : postList){
            resDtos.add(post.listFromEntity());
        }

        return resDtos;
    }

    @Transactional
    public void deletePost(Long id){
        postRepository.deleteById(id);
    }

    @Transactional
    public void updatePost(Long id, PostUpdateDto dto){
        Post post = postRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("존재하지 않는 게시물입니다."));
        post.updatePost(dto);
        postRepository.save(post);
    }
}
