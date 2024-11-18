package fact.it.moodservicee;

import fact.it.moodservicee.dto.MoodRequest;
import fact.it.moodservicee.dto.MoodResponse;
import fact.it.moodservicee.model.Mood;
import fact.it.moodservicee.repository.MoodRepository;
import fact.it.moodservicee.service.MoodService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestMoodService {

    @InjectMocks
    private MoodService moodService;

    @Mock
    private MoodRepository moodRepository;

    @Test
    public void testCreateMood() {
        // Arrange
        MoodRequest moodRequest = MoodRequest.builder()
                .userId("user123")
                .mood("Happy")
                .timestamp(LocalDateTime.now())
                .build();

        Mood mood = Mood.builder()
                .id("1")
                .userId(moodRequest.getUserId())
                .mood(moodRequest.getMood())
                .timestamp(moodRequest.getTimestamp())
                .build();

        when(moodRepository.save(any(Mood.class))).thenReturn(mood);

        // Act
        moodService.createMood(moodRequest);

        // Assert
        verify(moodRepository, times(1)).save(any(Mood.class));
    }

    @Test
    public void testGetAllMoods() {
        // Arrange
        Mood mood = Mood.builder()
                .id("1")
                .userId("user123")
                .mood("Happy")
                .timestamp(LocalDateTime.now())
                .build();

        when(moodRepository.findAll()).thenReturn(Arrays.asList(mood));

        // Act
        List<MoodResponse> moods = moodService.getAllMoods();

        // Assert
        assertEquals(1, moods.size());
        assertEquals("Happy", moods.get(0).getMood());
        assertEquals("user123", moods.get(0).getUserId());

        verify(moodRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllMoodsByTimestamp() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        Mood mood = Mood.builder()
                .id("1")
                .userId("user123")
                .mood("Sad")
                .timestamp(timestamp)
                .build();

        when(moodRepository.findByTimestamp(timestamp)).thenReturn(Arrays.asList(mood));

        // Act
        List<MoodResponse> moods = moodService.getAllMoodsByTimestamp(timestamp);

        // Assert
        assertEquals(1, moods.size());
        assertEquals("Sad", moods.get(0).getMood());
        assertEquals("user123", moods.get(0).getUserId());

        verify(moodRepository, times(1)).findByTimestamp(timestamp);
    }

    @Test
    public void testGetAllMoodsByUser() {
        // Arrange
        String userId = "user123";
        Mood mood = Mood.builder()
                .id("1")
                .userId(userId)
                .mood("Excited")
                .timestamp(LocalDateTime.now())
                .build();

        when(moodRepository.findByUserId(userId)).thenReturn(Arrays.asList(mood));

        // Act
        List<MoodResponse> moods = moodService.getAllMoodsByUser(userId);

        // Assert
        assertEquals(1, moods.size());
        assertEquals("Excited", moods.get(0).getMood());
        assertEquals(userId, moods.get(0).getUserId());

        verify(moodRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testUpdateMood() {
        // Arrange
        Mood existingMood = Mood.builder()
                .id("1")
                .userId("user123")
                .mood("Happy")
                .timestamp(LocalDateTime.now())
                .build();

        MoodRequest moodRequest = MoodRequest.builder()
                .userId("user456")
                .mood("Calm")
                .timestamp(LocalDateTime.now())
                .build();

        when(moodRepository.findById("1")).thenReturn(Optional.of(existingMood));
        when(moodRepository.save(any(Mood.class))).thenReturn(existingMood);

        // Act
        moodService.updateMood("1", moodRequest);

        // Assert
        verify(moodRepository, times(1)).findById("1");
        verify(moodRepository, times(1)).save(existingMood);
    }

    @Test
    public void testDeleteMood() {
        // Arrange
        String moodId = "1";
        when(moodRepository.existsById(moodId)).thenReturn(true);

        // Act
        moodService.deleteMood(moodId);

        // Assert
        verify(moodRepository, times(1)).existsById(moodId);
        verify(moodRepository, times(1)).deleteById(moodId);
    }
}
