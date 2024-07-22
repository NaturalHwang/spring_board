package beyond.board.post.service;

import beyond.board.post.domain.Post;
import beyond.board.post.repository.PostRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Configuration
// batch 작업 목록 정의
public class PostJobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private PostRepository postRepository;

//    batch 전용 테이블이 있어야됨.
    public Job excuteJob(){
        return jobBuilderFactory.get("excuteJob")
                .start(firstStep())
                .next(secondStep())
                .build();
    }

    @Bean
    public Step firstStep(){
        return stepBuilderFactory.get("firstStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("===예약 글쓰기 batch task1 시작===");
                    Page<Post> posts = postRepository.findByAppointment(Pageable.unpaged(), "Y");
                    for(Post p : posts){
                        if(p.getAppointmentTime().isBefore(LocalDateTime.now())){
                            p.updateAppointment("N");
                        }
                    }
                    System.out.println("예약 글쓰기 task1 종료");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step secondStep(){
        return stepBuilderFactory.get("secondStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("===예약 글쓰기 batch task2 시작===");
                    System.out.println("===예약 글쓰기 task2종료");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
