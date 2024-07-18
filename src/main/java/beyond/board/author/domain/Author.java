package beyond.board.author.domain;

import beyond.board.author.dto.AuthorDetailDto;
import beyond.board.author.dto.AuthorResDto;
import beyond.board.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Column(nullable = false, length = 13)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    public Author(String name, String email, String password, String role){
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = Role.valueOf(role);
    }

    public AuthorDetailDto detFromEntity(){
        LocalDateTime createTime = this.createdTime;
        String time = createTime.getYear() + "년 " + createTime.getMonthValue() + "월 " +  createTime.getDayOfMonth() + "일";
        return new AuthorDetailDto(this.id, this.name, this.email, this.password, time);
    }
    public AuthorResDto listFromEntity(){
        return new AuthorResDto(this.id, this.name, this.email);
    }
}