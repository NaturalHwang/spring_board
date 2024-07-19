package beyond.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostListResDto {
    private Long id;
    private String title;
//    Author 객체 그 자체를 return 하게 되면 Author 안에 Post가 재참조되어, 순환참조 이슈 발생.
    private String author_email;
//    private Author author;
//    public PostResDto(Long id, String title, String email){
//        this.id = id;
//        this.title = title;
//        this.email = email;
//    }
}
