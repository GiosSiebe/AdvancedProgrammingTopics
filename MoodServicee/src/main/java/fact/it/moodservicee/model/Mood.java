package fact.it.moodservicee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Document(value = "mood")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Mood {
    private String id;
    private String userId;
    private String mood;
    private LocalDateTime timestamp;
}
