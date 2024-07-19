package beyond.board.author.domain;

import beyond.board.author.dto.AuthorListResDto;
import beyond.board.author.dto.AuthorUpdateDto;
import beyond.board.common.BaseTimeEntity;
import beyond.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder // builder 어노테이션을 통해 매개변수의 순서, 매개변수의 개수 등을 유연하게 세팅하여 생성자로서 활용
@NoArgsConstructor // buider와 NoArgs, AllArgs는 세트로 다님.
@AllArgsConstructor
public class Author extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 30, unique = true, updatable = false)
    private String email;

    @Column(nullable = false, length = 13)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

//    일반적으로 부모 엔티티(자식 객체에 영향을 끼칠 수 있는 엔티티)에 Cascade 옵션을 설정
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts;

//    @Builder
//    public Author(String name, String email, String password, Role role){
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//    }

//    Dto에서 조립하는 방향으로 변경
//    public AuthorDetailDto detFromEntity(){
////        LocalDateTime createTime = this.createdTime;
////        String time = createTime.getYear() + "년 " + createTime.getMonthValue() + "월 " +  createTime.getDayOfMonth() + "일";
//        return new AuthorDetailDto(this.id, this.name, this.email, this.password, this.createdTime);
//    }

    public void updateAuthor(AuthorUpdateDto authorUpdateDto){
        String name = authorUpdateDto.getName();
        String password = authorUpdateDto.getPassword();

        if(!name.equals(this.name)){
            this.name = name;
        }
        if(name.isEmpty()){
            throw new IllegalArgumentException("이름은 반드시 입력해주세요");
        }
        if(password.length() < 8){
            throw new IllegalArgumentException("비밀번호는 8자리 이상입니다.");
        }
        if(!password.equals(this.password)){
            this.password = password;
        }
    }

    public AuthorListResDto listFromEntity(){
        AuthorListResDto authorListResDto = AuthorListResDto.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .build();
        return authorListResDto;
//        return new AuthorListResDto(this.id, this.name, this.email);
    }
}