package beyond.board.post.dto;

import beyond.board.author.domain.Author;
import beyond.board.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class PostReqDto {
    private String title;
    private String contents;
    private String email;


    public Post toEntity(Author author){
        Post post = new Post(title, contents, author);
        return post;
    }
}
