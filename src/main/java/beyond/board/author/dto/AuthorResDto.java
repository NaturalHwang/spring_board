package beyond.board.author.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthorResDto {
    private Long id;
    private String name;
    private String email;
}
