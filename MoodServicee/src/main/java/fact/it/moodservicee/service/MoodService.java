package fact.it.moodservicee.service;

import fact.it.moodservicee.dto.MoodRequest;
import fact.it.moodservicee.dto.MoodResponse;
import fact.it.moodservicee.model.Mood;
import fact.it.moodservicee.repository.MoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoodService {
    private final MoodRepository moodRepository;

    public void createMood(MoodRequest moodRequest) {
        Mood mood = Mood.builder()
                .userId(moodRequest.getUserId())
                .timestamp(moodRequest.getTimestamp())  // Correct the field name to 'timestamp'
                .mood(moodRequest.getMood())
                .build();

        moodRepository.save(mood);
    }

    public List<MoodResponse> getAllMoods() {
        List<Mood> moods = moodRepository.findAll();

        return moods.stream().map(this::mapToMoodResponse).toList();
    }

    public List<MoodResponse> getAllMoodsByTimestamp(LocalDateTime timestamp) {
        List<Mood> moods = moodRepository.findByTimestamp(timestamp);

        return moods.stream().map(this::mapToMoodResponse).toList();
    }

    public List<MoodResponse> getAllMoodsByUser(String userId) {
        List<Mood> moods = moodRepository.findByUserId(userId);

        return moods.stream().map(this::mapToMoodResponse).toList();
    }

    public void updateMood(String id, MoodRequest moodRequest) {
        Optional<Mood> existingMood = moodRepository.findById(id);

        if (existingMood.isPresent()) {
            Mood mood = existingMood.get();
            mood.setUserId(moodRequest.getUserId());
            mood.setTimestamp(moodRequest.getTimestamp());
            mood.setMood(moodRequest.getMood());

            moodRepository.save(mood);
        } else {
            throw new RuntimeException("Mood entry with ID " + id + " not found");
        }
    }

    public void deleteMood(String id) {
        if (moodRepository.existsById(id)) {
            moodRepository.deleteById(id);
        } else {
            throw new RuntimeException("Mood entry with ID " + id + " not found");
        }
    }

    private MoodResponse mapToMoodResponse(Mood mood) {
        return MoodResponse.builder()
                .id(mood.getId())
                .mood(mood.getMood())
                .userId(mood.getUserId())
                .timestamp(mood.getTimestamp())
                .build();
    }
}
