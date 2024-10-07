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
public class RecommendationResponse {
    private Long id;                        // Unique identifier for the recommendation
    private String userId;                    // ID of the user for whom the recommendation is made
    private MoodResponse mood;               // Mood response object
    private ActivityResponse activity;       // Activity response object
    private LocalDateTime timestamp;                // Timestamp of the recommendation creation as a String
}
