package fact.it.moodservicee.repository;

import fact.it.moodservicee.model.Mood;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MoodRepository extends MongoRepository<Mood, String> {
    List<Mood> findByTimestamp(LocalDateTime timestamp);
    List<Mood> findByUserId(String userId);
}
