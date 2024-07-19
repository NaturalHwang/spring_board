package beyond.board.post.controller;

import beyond.board.post.dto.PostListResDto;
import beyond.board.post.dto.PostSaveReqDto;
import beyond.board.post.dto.PostUpdateDto;
import beyond.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;

    }

    @GetMapping("/post/create")
    public String postCreateScreen(){
        return "post/post_create";
    }

//    @PostMapping("/post/create")
//    public String createPost(@RequestBody PostSaveReqDto reqDto){
//        Post post = postService.postCreate(reqDto);
//        return "ok";
//    }
    @PostMapping("/post/create")
    public String createPost(PostSaveReqDto reqDto){
        postService.postCreate(reqDto);
        return "redirect:/post/list";
    }

//    @GetMapping("/post/detail/{id}")
//    public PostDetResDto postDetail(@PathVariable Long id){
//        return postService.postDetail(id);
//    }
    @GetMapping("/post/detail/{id}")
    public String postDetail(@PathVariable Long id, Model model){
        model.addAttribute("post", postService.postDetail(id));
        return "post/post_detail";
    }

//    @GetMapping("/post/list")
//    public List<PostListResDto> postList(){
//        return postService.postList();
//    }
    @GetMapping("/post/list")
    public String postList(Model model){
        List<PostListResDto> postList = postService.postList();
        model.addAttribute("postList", postList);
        return "/post/post_list";
    }

    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return "redirect:/post/list";
    }

    @PostMapping("/post/update/{id}")
    public String postUpdate(@PathVariable Long id,
                             @ModelAttribute PostUpdateDto updateDto){
        postService.updatePost(id, updateDto);
        return "redirect:/post/detail/" + id;
    }
}
