package beyond.board.post.dto;

import beyond.board.author.domain.Author;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class PostResDto {
    private Long id;
    private String title;
    private String email;


    public PostResDto(Long id, String title, String email){
        this.id = id;
        this.title = title;
        this.email = email;
    }
}
