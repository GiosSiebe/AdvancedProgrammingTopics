package fact.it.recommendationservice;

import fact.it.recommendationservice.dto.ActivityResponse;
import fact.it.recommendationservice.dto.MoodResponse;
import fact.it.recommendationservice.dto.RecommendationResponse;
import fact.it.recommendationservice.service.RecommendationService;
import fact.it.recommendationservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class RecommendationServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private UserService userService;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private ResponseSpec responseSpec;

    @InjectMocks
    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRecommendationsByUserId_UserNotFound() {
        // Mock userService behavior
        when(userService.userExists(anyString())).thenReturn(false); // Simulate user does not exist

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            recommendationService.getRecommendationsByUserId("unknownUserId");
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());
    }

    @Test
    void testGetRecommendationsByUserId_NoMoods() {
        // Mock userService behavior
        when(userService.userExists(anyString())).thenReturn(true); // Simulate user exists
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(MoodResponse.class)).thenReturn(Flux.empty()); // No moods found

        List<RecommendationResponse> recommendations = recommendationService.getRecommendationsByUserId("userId");

        assertEquals(0, recommendations.size());
    }

    @Test
    void testGetRecommendationsByUserId_WithMoodsAndActivities() {
        // Mock userService behavior
        when(userService.userExists(anyString())).thenReturn(true); // Simulate user exists
        when(webClient.get()).thenReturn(requestHeadersUriSpec);

        // Mock moods response
        MoodResponse mood = new MoodResponse(); // Initialize with your properties
        mood.setMood("happy"); // Set the mood
        when(requestHeadersUriSpec.uri("http://some-mood-uri")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(MoodResponse.class)).thenReturn(Flux.just(mood));

        // Mock activities response
        ActivityResponse activity = new ActivityResponse(); // Initialize with your properties
        activity.setCategory("happy"); // Ensure it matches the mood
        when(requestHeadersUriSpec.uri("http://some-activity-uri")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(ActivityResponse.class)).thenReturn(Flux.just(activity));

        List<RecommendationResponse> recommendations = recommendationService.getRecommendationsByUserId("userId");

        assertEquals(1, recommendations.size()); // Expect one recommendation based on the matching mood and activity
    }
}
