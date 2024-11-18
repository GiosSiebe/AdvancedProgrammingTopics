package fact.it.recommendationservice;

import fact.it.recommendationservice.dto.ActivityResponse;
import fact.it.recommendationservice.dto.MoodResponse;
import fact.it.recommendationservice.dto.RecommendationResponse;
import fact.it.recommendationservice.service.RecommendationService;
import fact.it.recommendationservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {

    @InjectMocks
    private RecommendationService recommendationService;

    @Mock
    private WebClient webClient;

    @Mock
    private UserService userService;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(recommendationService, "userServiceBaseUrl", "localhost:8082");
        ReflectionTestUtils.setField(recommendationService, "activityServiceBaseUrl", "localhost:8083");
        ReflectionTestUtils.setField(recommendationService, "moodServiceBaseUrl", "localhost:8084");
    }

    @Test
    public void testGetRecommendationsByUserId_Success() {
        // Arrange
        String userId = "123";

        MoodResponse[] mockMoods = {
                new MoodResponse("1", "user1", "Happy", LocalDateTime.now()),
                new MoodResponse("2", "user2", "Sad", LocalDateTime.now()),
        };
        ActivityResponse[] mockActivities = {
                new ActivityResponse(1L, "Running", "Outdoor running activity", "Happy", 30, "High"),
                new ActivityResponse(2L, "Cycling", "Outdoor cycling activity", "Sad", 45, "Medium")
        };

        when(userService.userExists(userId)).thenReturn(true);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // Mock response for moods (should return a Flux with mockMoods)
        when(responseSpec.bodyToFlux(MoodResponse.class)).thenReturn(Flux.just(mockMoods));

        // Mock response for activities (should return a Mono with mockActivities)
        when(responseSpec.bodyToFlux(ActivityResponse.class)).thenReturn(Flux.just(mockActivities));

        // Act
        List<RecommendationResponse> result = recommendationService.getRecommendationsByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Happy", result.get(0).getMood().getMood());
        assertEquals("Sad", result.get(1).getMood().getMood());

        verify(webClient, times(2)).get(); // Verify that webClient is called twice (once for moods and once for activities)
    }

    @Test
    public void testGetRecommendationsByUserId_UserNotFound() {
        // Arrange
        String userId = "123";
        when(userService.userExists(userId)).thenReturn(false);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> recommendationService.getRecommendationsByUserId(userId));
    }

    @Test
    public void testGetRecommendationsByUserId_NoMoods() {
        // Arrange
        String userId = "123";
        when(userService.userExists(userId)).thenReturn(true);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // Mocking an empty array of moods, not null
        when(responseSpec.bodyToFlux(MoodResponse.class)).thenReturn(Flux.empty()); // Return empty Flux for moods

        // Mocking activities (you can mock an empty or valid response as needed)
        ActivityResponse[] mockActivities = {
                new ActivityResponse(1L, "Running", "Outdoor running activity", "Happy", 30, "High")
        };
        lenient().when(responseSpec.bodyToMono(ActivityResponse[].class)).thenReturn(Mono.just(mockActivities)); // Mock activities

        // Act
        List<RecommendationResponse> result = recommendationService.getRecommendationsByUserId(userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty()); // Should be empty since there are no moods

        verify(webClient, times(1)).get(); // Only one call to webClient, for moods
    }

    @Test
    public void testGetRecommendationsByUserId_ErrorFetchingMoods() {
        // Arrange
        String userId = "123";
        when(userService.userExists(userId)).thenReturn(true);

        // Mock the WebClient to return an error for fetching moods
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // Mocking bodyToFlux() to simulate an error scenario (empty or null response)
        when(responseSpec.bodyToFlux(MoodResponse.class)).thenReturn(Flux.error(new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error fetching moods")));

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> recommendationService.getRecommendationsByUserId(userId));
    }

    @Test
    public void testGetRecommendationsByUserId_ErrorFetchingActivities() {
        // Arrange
        String userId = "123";
        when(userService.userExists(userId)).thenReturn(true);

        // Mock the WebClient to return an error for fetching activities
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // Mock activities to throw an error
        when(responseSpec.bodyToFlux(ActivityResponse.class)).thenReturn(Flux.error(new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error fetching moods")));

        // Mock the WebClient to return moods successfully
        MoodResponse[] mockMoods = {
                new MoodResponse("1", "user1", "Happy", LocalDateTime.now())
        };

        when(responseSpec.bodyToFlux(MoodResponse.class)).thenReturn(Flux.just(mockMoods));

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> recommendationService.getRecommendationsByUserId(userId));
    }



}
