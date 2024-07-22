package beyond.board.post.dto;

import beyond.board.author.domain.Author;
import beyond.board.post.domain.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@Data
public class PostSaveReqDto {
//    private String title;
//    private String contents;
//    private String email;
    private String title;
    private String contents;
//    추후 로그인 기능 구현 이후에 없어질 Dto
    private String email;

    private String appointment;
    private String appointmentTime;

//    public Post toEntity(Author author){
//        Post post = new Post(title, contents, author);
//        return post;
//    }
    public Post toEntity(Author author, LocalDateTime appointmentTime){
        if(appointment.equals("Y")){
            return Post.builder()
                    .title(this.title)
                    .contents(this.contents)
                    .appointment(this.appointment)
//                    .appointmentTime(LocalDateTime.parse(this.appointmentTime))
                    .appointmentTime(appointmentTime)
                    .author(author)
                    .build();
        } else{
            return Post.builder()
                    .title(this.title)
                    .contents(this.contents)
                    .author(author)
                    .appointment(this.appointment)
                    .appointmentTime(null)
                    .build();
        }
    }
}
