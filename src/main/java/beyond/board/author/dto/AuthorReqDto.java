package beyond.board.author.dto;

import beyond.board.author.domain.Author;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthorReqDto {
    private String name;
    private String email;
    private String password;
    private String role;

    public Author toEntity(){
        Author author = new Author(this.name, this.email, this.password, this.role);
        return author;
    }
}

