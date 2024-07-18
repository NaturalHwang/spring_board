package beyond.board.author.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDetailDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String createdTime;

    public AuthorDetailDto(String name, String email, String password, String createdTime){
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdTime = createdTime;
    }
}
