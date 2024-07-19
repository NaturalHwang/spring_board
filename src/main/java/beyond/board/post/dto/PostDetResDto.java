package beyond.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PostDetResDto {
    private Long id;
    private String title;
    private String contents;
//    private String createdTime;
//    private String updatedTime;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String author_email;

//    public PostDetailDto(String title, String contents, String createdTime, String updatedTime){
//        this.title = title;
//        this.contents = contents;
//        this.createdTime = createdTime;
//        this.updatedTime = updatedTime;
//    }
}
