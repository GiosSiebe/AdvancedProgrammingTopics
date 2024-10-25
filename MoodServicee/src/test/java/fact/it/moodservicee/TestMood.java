package fact.it.moodservicee;

import fact.it.moodservicee.model.Mood;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MoodTest {

    private Mood mood;

    @BeforeEach
    void setUp() {
        mood = new Mood();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(mood);
        assertNull(mood.getId());
        assertNull(mood.getUserId());
        assertNull(mood.getMood());
        assertNull(mood.getTimestamp());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        mood = new Mood("1", "user123", "Happy", now);

        assertEquals("1", mood.getId());
        assertEquals("user123", mood.getUserId());
        assertEquals("Happy", mood.getMood());
        assertEquals(now.getYear(), mood.getTimestamp().getYear());
        assertEquals(now.getMonth(), mood.getTimestamp().getMonth());
        assertEquals(now.getDayOfMonth(), mood.getTimestamp().getDayOfMonth());
        assertEquals(now.getHour(), mood.getTimestamp().getHour());
        assertEquals(now.getMinute(), mood.getTimestamp().getMinute());
    }

    @Test
    void testSettersAndGetters() {
        LocalDateTime timestamp = LocalDateTime.now().minusDays(1);
        mood.setId("2");
        mood.setUserId("user456");
        mood.setMood("Sad");
        mood.setTimestamp(timestamp);

        assertEquals("2", mood.getId());
        assertEquals("user456", mood.getUserId());
        assertEquals("Sad", mood.getMood());
        assertEquals(timestamp.getYear(), mood.getTimestamp().getYear());
        assertEquals(timestamp.getMonth(), mood.getTimestamp().getMonth());
        assertEquals(timestamp.getDayOfMonth(), mood.getTimestamp().getDayOfMonth());
    }

    @Test
    void testBuilder() {
        LocalDateTime now = LocalDateTime.now();
        mood = Mood.builder()
                .id("3")
                .userId("user789")
                .mood("Excited")
                .timestamp(now)
                .build();

        assertEquals("3", mood.getId());
        assertEquals("user789", mood.getUserId());
        assertEquals("Excited", mood.getMood());
        assertEquals(now.getYear(), mood.getTimestamp().getYear());
        assertEquals(now.getMonth(), mood.getTimestamp().getMonth());
        assertEquals(now.getDayOfMonth(), mood.getTimestamp().getDayOfMonth());
    }
}
