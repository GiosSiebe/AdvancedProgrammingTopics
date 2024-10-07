package fact.it.recommendationservice.repository;

import fact.it.recommendationservice.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
}
