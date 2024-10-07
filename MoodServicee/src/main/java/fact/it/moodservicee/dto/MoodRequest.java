package fact.it.moodservicee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoodRequest {
    private String userId;
    private String mood;
    private LocalDateTime timestamp;
}
