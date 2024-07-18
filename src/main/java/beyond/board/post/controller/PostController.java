package beyond.board.post.controller;

import beyond.board.post.domain.Post;
import beyond.board.post.dto.PostDetailDto;
import beyond.board.post.dto.PostReqDto;
import beyond.board.post.dto.PostResDto;
import beyond.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;

    }

    @PostMapping("/create")
    public String createPost(@RequestBody PostReqDto reqDto){
        Post post = postService.createPost(reqDto);

        return "ok";
    }

    @GetMapping("/detail/{id}")
    public PostDetailDto postDetail(@PathVariable Long id){
        return postService.postDetail(id);
    }

    @GetMapping("/list")
    public List<PostResDto> postList(){
        return postService.postList();
    }
}
