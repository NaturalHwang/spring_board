package beyond.board.author.controller;

import beyond.board.author.dto.AuthorDetailDto;
import beyond.board.author.dto.AuthorListResDto;
import beyond.board.author.dto.AuthorSaveReqDto;
import beyond.board.author.dto.AuthorUpdateDto;
import beyond.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping("/author/create")
    public String authorRegister(){
        return "author/author_create";
    }

    @PostMapping("/author/create")
    public String authorCreate(AuthorSaveReqDto authorSaveReqDto, Model model){
        try {
            authorService.authorCreate(authorSaveReqDto);

        } catch (IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            return "common/error";
        }
        return "redirect:/author/list";
    }

    @GetMapping("/author/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model){
        AuthorDetailDto author = authorService.authorDetail(id);
        model.addAttribute("author", author);
        return "author/author_detail";
    }

//    @GetMapping("/detail/{id}")
//    public AuthorDetailDto authorDetail(@PathVariable Long id){
//        return authorService.authorDetail(id);
//    }

    @GetMapping("author/list")
    public String authorList(Model model){
        List<AuthorListResDto> authorListResDtos = authorService.authorList();
        model.addAttribute("authorList", authorListResDtos);
        return "author/author_list";
    }

//    @GetMapping("/list")
//    public List<AuthorListResDto> authorList(){
//        return authorService.authorList();
//    }

    @GetMapping("/author/delete/{id}")
    public String authorDelete(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return "redirect:/author/list";
    }

    @PostMapping("/author/update/{id}")
    public String authorUpdate(@PathVariable Long id,
                               @ModelAttribute AuthorUpdateDto authorUpdateDto,
                               Model model){
        try {
            authorService.updateAuthor(id, authorUpdateDto);
            return "redirect:/author/detail/" + id;
        } catch (IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            return "/common/error";
        }
    }
}
