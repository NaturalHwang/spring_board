package beyond.board.post.domain;

import beyond.board.author.domain.Author;
import beyond.board.author.dto.AuthorDetailDto;
import beyond.board.post.dto.PostDetailDto;
import beyond.board.post.dto.PostResDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    String title;
    @Column(nullable = false, length = 3000)
    String contents;

    @JoinColumn(name = "author_id")
    @ManyToOne
    private Author author;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public Post(String title, String contents, Author author){
        this.title = title;
        this.contents = contents;
        this.author = author;
    }

    public PostDetailDto detFromEntity(){
        LocalDateTime createTime = this.createdTime;
        LocalDateTime updateTime = this.updatedTime;

        String createdTime = createTime.getYear() + "년 " + createTime.getMonthValue() + "월 " +  createTime.getDayOfMonth() + "일";
        String updatedTime = updateTime.getYear() + "년 " + updateTime.getMonthValue() + "월 " +  updateTime.getDayOfMonth() + "일";
        return new PostDetailDto(this.id, this.title, this.contents, createdTime, updatedTime);
    }

    public PostResDto listFromEntity(){
        return new PostResDto(this.id, this.title, author.getEmail());
    }
}
