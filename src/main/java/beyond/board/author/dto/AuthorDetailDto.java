package beyond.board.author.dto;

import beyond.board.author.domain.Author;
import beyond.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDetailDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdTime;
    private Role role;
    private int count;

    public AuthorDetailDto fromEntityToDto(Author author){
        return AuthorDetailDto.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .password(author.getPassword())
                .role(author.getRole())
                .createdTime(author.getCreatedTime())
                .count(author.getPosts().size())
                .build();
    }
//    private String createdTime;

//    public AuthorDetailDto(String name, String email, String password, String createdTime){
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.createdTime = createdTime;
//    }
}
