package fact.it.recommendationservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the recommendation

    @Column(nullable = false)
    private String userId; // ID of the user for whom the recommendation is made

    @Column(nullable = false)
    private String mood; // Mood associated with the recommendation (as a String)

    @Column(nullable = false)
    private String activity; // Activity associated with the recommendation (as a String)

    @Column(nullable = false)
    private LocalDateTime timestamp; // Timestamp of the recommendation creation
}
