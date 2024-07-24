package beyond.board.post.domain;

import beyond.board.author.domain.Author;
import beyond.board.common.BaseTimeEntity;
import beyond.board.post.dto.PostDetResDto;
import beyond.board.post.dto.PostListResDto;
import beyond.board.post.dto.PostUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30)
    String title;
    @Column(nullable = false, length = 3000)
    String contents;

//    연관 관계에 주인은 fk가 있는 Post다(fk를 여기서 관리하므로).
    @JoinColumn(name = "author_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    private String appointment;
    private LocalDateTime appointmentTime;

//    public Post(String title, String contents, Author author){
//        this.title = title;
//        this.contents = contents;
//        this.author = author;
//    }

    public PostDetResDto detFromEntity(){
//        LocalDateTime createTime = this.createdTime;
//        LocalDateTime updateTime = this.updatedTime;

//        String createdTime = createTime.getYear() + "년 " + createTime.getMonthValue() + "월 " +  createTime.getDayOfMonth() + "일 " + createTime.getHour() + ":" + createTime.getMinute() + ":" + createTime.getSecond();
//        String updatedTime = updateTime.getYear() + "년 " + updateTime.getMonthValue() + "월 " +  updateTime.getDayOfMonth() + "일 " + updateTime.getHour() + ":" + updateTime.getMinute() + ":" + updateTime.getSecond();
        return PostDetResDto.builder()
//                this가 빠져있어도 제대로 들어가는 이유는 자동으로 매개변수와 비교할 때 사용되지만
//                이 경우에는 매개변수가 없어서 제대로 들어감
//                .id(id) // 이렇게 써도 사실상 id(this.id)와 같음
                .id(this.id)
                .title(this.title)
                .contents(this.contents)
                .createdTime(this.getCreatedTime())
                .updatedTime(this.getUpdatedTime())
                .author_email(this.author.getEmail())
                .build();
//        return new PostDetailDto(this.id, this.title, this.contents, createdTime, updatedTime);
    }

    public void updatePost(PostUpdateDto updateDto){
        if(!updateDto.getTitle().isEmpty()) this.title = updateDto.getTitle();
        if(!updateDto.getContents().isEmpty()) this.contents = updateDto.getContents();
    }

//    public void updateAppointment(){
//        this.appointment = "N";
////        this.appointmentTime = null;
//    }

    public void updateAppointment(String yn){
        this.appointment = yn;
    }

    public PostListResDto listFromEntity(){
//        return PostListResDto.builder().id(this.id).title(this.title).author_email(this.author.getEmail()).build();
//        순환참조 실험 코드
//        return PostListResDto.builder().id(this.id).title(this.title).author(this.author).build();
        return PostListResDto.builder().id(this.id).title(this.title).author_email(this.author.getEmail()).build();
//        return new PostResDto(this.id, this.title, author.getEmail());
    }
}
