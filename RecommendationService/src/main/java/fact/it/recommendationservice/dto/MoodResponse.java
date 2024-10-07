package fact.it.recommendationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoodResponse {
    private String id;
    private String userId;
    private String mood;
    private LocalDateTime timestamp;
}
