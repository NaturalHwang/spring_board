package beyond.board.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "common/home";
    }

    @GetMapping("/error")
    public String error(){
        return "common/error";
    }
}
