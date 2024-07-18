package beyond.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostDetailDto {
    private Long id;
    private String title;
    private String contents;
    private String createdTime;
    private String updatedTime;

    public PostDetailDto(String title, String contents, String createdTime, String updatedTime){
        this.title = title;
        this.contents = contents;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
