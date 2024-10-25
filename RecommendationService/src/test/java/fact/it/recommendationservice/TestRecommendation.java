package fact.it.recommendationservice;

import fact.it.recommendationservice.model.Recommendation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationTest {

    private Recommendation recommendation;

    @BeforeEach
    void setUp() {
        recommendation = new Recommendation();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(recommendation);
        assertNull(recommendation.getId());
        assertNull(recommendation.getUserId());
        assertNull(recommendation.getMood());
        assertNull(recommendation.getActivity());
        assertNull(recommendation.getTimestamp());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        recommendation = new Recommendation(1L, "user123", "Happy", "Jogging", now);

        assertEquals(1L, recommendation.getId());
        assertEquals("user123", recommendation.getUserId());
        assertEquals("Happy", recommendation.getMood());
        assertEquals("Jogging", recommendation.getActivity());
        assertEquals(now.getYear(), recommendation.getTimestamp().getYear());
        assertEquals(now.getMonth(), recommendation.getTimestamp().getMonth());
        assertEquals(now.getDayOfMonth(), recommendation.getTimestamp().getDayOfMonth());
    }

    @Test
    void testSettersAndGetters() {
        LocalDateTime timestamp = LocalDateTime.now().minusDays(1);
        recommendation.setId(2L);
        recommendation.setUserId("user456");
        recommendation.setMood("Sad");
        recommendation.setActivity("Reading");
        recommendation.setTimestamp(timestamp);

        assertEquals(2L, recommendation.getId());
        assertEquals("user456", recommendation.getUserId());
        assertEquals("Sad", recommendation.getMood());
        assertEquals("Reading", recommendation.getActivity());
        assertEquals(timestamp.getYear(), recommendation.getTimestamp().getYear());
        assertEquals(timestamp.getMonth(), recommendation.getTimestamp().getMonth());
        assertEquals(timestamp.getDayOfMonth(), recommendation.getTimestamp().getDayOfMonth());
    }
}
