package fact.it.recommendationservice;

import fact.it.recommendationservice.dto.ActivityResponse;
import fact.it.recommendationservice.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ActivityServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.Builder webClientBuilder; // Mock the WebClient builder

    @Mock
    private RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RequestHeadersSpec requestHeadersSpec;

    @Mock
    private ResponseSpec responseSpec;

    @InjectMocks
    private ActivityService activityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock the WebClient behavior
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    void testGetAllActivities() {
        // Mock the WebClient behavior
        ActivityResponse activity1 = new ActivityResponse(); // Initialize with your properties
        ActivityResponse activity2 = new ActivityResponse(); // Initialize with your properties
        ActivityResponse[] mockActivities = {activity1, activity2};

        when(responseSpec.bodyToMono(ActivityResponse[].class)).thenReturn(Mono.just(mockActivities));

        List<ActivityResponse> activities = activityService.getAllActivities();

        assertEquals(2, activities.size());
    }

    @Test
    void testFindByCategory() {
        // Mock the WebClient behavior
        ActivityResponse activity = new ActivityResponse(); // Initialize with your properties
        ActivityResponse[] mockActivities = {activity};

        when(requestHeadersUriSpec.uri(anyString(), anyString())).thenReturn(requestHeadersSpec);
        when(responseSpec.bodyToMono(ActivityResponse[].class)).thenReturn(Mono.just(mockActivities));

        List<ActivityResponse> activities = activityService.findByCategory("someCategory");

        assertEquals(1, activities.size());
    }
}
