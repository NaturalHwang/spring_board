package beyond.board.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostUpdateDto {
    String title;
    String contents;
    String appointment;
    LocalDateTime appointmentTime;
}
