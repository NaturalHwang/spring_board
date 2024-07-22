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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        String appointment = null;
        LocalDateTime appointmentTime = null;
        if(reqDto.getAppointment().equals("Y") && !reqDto.getAppointmentTime().isEmpty()){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            appointmentTime = LocalDateTime.parse(reqDto.getAppointmentTime(), dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            if(appointmentTime.isBefore(now)){ // isBefore, isAfter
                throw new IllegalArgumentException("시간 입력이 잘못되었습니다");
            }
        }
        System.out.println(reqDto);
//        Post post = reqDto.toEntity(author);
//        return postRepository.save(post);
        Post post = postRepository.save(reqDto.toEntity(author, appointmentTime));
        return post;
    }

    public PostDetResDto postDetail(Long id){
//        Optional<Post> optPost = postRepository.findById(id);
//        Post post = optPost.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다"));
        Post post = postRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("존재하지 않는 게시물입니다."));
        return post.detFromEntity();
    }

//    public List<PostListResDto> postList(){
////        List<Post> postList = postRepository.findAllFetch();
//        List<Post> postList = postRepository.findByAppointment();
//        List<PostListResDto> resDtos = new ArrayList<>();
//
//        for(Post post : postList){
//            resDtos.add(post.listFromEntity());
//        }
//
//        return resDtos;
//    }
    public Page<PostListResDto> postList(Pageable pageable){
//        List<Post> posts = postRepository.findAllLeftJoin();
//        List<PostListResDto> postListResDtos = new ArrayList<>();
//        for( Post p : posts ){
//            postListResDtos.add(p.listFromEntity());
//        }

    // Page<Post> posts = postRepository.findAll(pageable);
        Page<Post> posts = postRepository.findByAppointment(pageable, "N");
        Page<PostListResDto> postListResDtos = posts.map(a->a.listFromEntity());

        return postListResDtos;
    }

    public Page<PostListResDto> postListPage(Pageable pageable){
        Page<Post> posts = postRepository.findAll(pageable);
        Page<PostListResDto> postListResDtos = posts.map(a->a.listFromEntity());
        return postListResDtos;
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
