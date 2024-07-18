package beyond.board.author.controller;

import beyond.board.author.dto.AuthorDetailDto;
import beyond.board.author.dto.AuthorReqDto;
import beyond.board.author.dto.AuthorResDto;
import beyond.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping("/create")
    public String authorCreate(@RequestBody AuthorReqDto authorReqDto){
        authorService.createMember(authorReqDto);
        return "ok";
    }

    @GetMapping("/detail/{id}")
    public AuthorDetailDto authorDetail(@PathVariable Long id){
        return authorService.authorDetail(id);
    }

    @GetMapping("/list")
    public List<AuthorResDto> authorList(){
        return authorService.authorList();
    }
}
