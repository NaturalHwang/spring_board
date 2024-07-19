package beyond.board.author.dto;

import beyond.board.author.domain.Author;
import beyond.board.author.domain.Role;
import beyond.board.post.domain.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AuthorSaveReqDto {
    private String name;
    private String email;
    private String password;
//    사용자가 String 요청해도 Role클래스 자동 형변환
    private Role role;
    private List<Post> posts;

//    public Author toEntity(){
//        Author author = new Author(this.name, this.email, this.password, this.role);
//        return author;
//    }
//    builder 사용 시 아래처럼 유연하게 설정 가능(순서 상관 X, 매개변수 수 설정 가능)
//    builder 의 직접 구현은 basic 에 Hello Class에 있음.
    public Author toEntity(){
        Author author = Author.builder()
                .name(this.name) // 여기에 없는 매개변수들은 DB에 Null로 들어감
                .password(this.password)
                .email(email)
                .role(this.role)
                .posts(new ArrayList<>())
                .build();
        return author;
    }
}

