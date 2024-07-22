package beyond.board.post.controller;

import beyond.board.post.dto.PostListResDto;
import beyond.board.post.dto.PostSaveReqDto;
import beyond.board.post.dto.PostUpdateDto;
import beyond.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
//    @GetMapping("/post/list")
//    public String postList(Model model){
//        List<PostListResDto> postList = postService.postList();
//        model.addAttribute("postList", postList);
//        return "/post/post_list";
//    }
    @GetMapping("/post/list")
    public String postList(Model model, @PageableDefault(size = 8,  sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("postList", postService.postList(pageable));
        return "post/post_list";
    }

    @GetMapping("/post/list/page")
    @ResponseBody
    // Pageable 요청 방법 : localhost:8080/post/list/page?size=10&page=0
    public Page<PostListResDto> postListPage(@PageableDefault(size = 10,  sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable){ // Page 객체 자체가 List로 되어 있어서 굳이 List를 다시 감쌀 필요는 없다
        return postService.postListPage(pageable);
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
