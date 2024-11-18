package fact.it.recommendationservice;

import fact.it.recommendationservice.dto.ActivityResponse;
import fact.it.recommendationservice.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {

    @InjectMocks
    private ActivityService activityService;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(activityService, "activityServiceBaseUrl", "localhost:8081");
    }

    @Test
    public void testGetAllActivities_Success() {
        // Arrange
        ActivityResponse[] mockActivities = {
                new ActivityResponse(1L, "Running", "Outdoor running activity", "Sports", 30, "High"),
                new ActivityResponse(2L, "Reading", "Indoor reading activity", "Leisure", 60, "Low")
        };

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ActivityResponse[].class)).thenReturn(Mono.just(mockActivities));

        // Act
        List<ActivityResponse> result = activityService.getAllActivities();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Running", result.get(0).getActivity());
        assertEquals("Reading", result.get(1).getActivity());

        verify(webClient, times(1)).get();
    }

    @Test
    public void testFindByCategory_Success() {
        // Arrange
        String category = "Sports";
        ActivityResponse[] mockActivities = {
                new ActivityResponse(1L, "Running", "Outdoor running activity", category, 30, "High"),
                new ActivityResponse(2L, "Cycling", "Outdoor cycling activity", category, 60, "Medium")
        };

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), eq(category))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ActivityResponse[].class)).thenReturn(Mono.just(mockActivities));

        // Act
        List<ActivityResponse> result = activityService.findByCategory(category);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Running", result.get(0).getActivity());
        assertEquals("Cycling", result.get(1).getActivity());

        verify(webClient, times(1)).get();
    }

    @Test
    public void testGetAllActivities_EmptyResult() {
        // Arrange
        ActivityResponse[] mockActivities = {};

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ActivityResponse[].class)).thenReturn(Mono.just(mockActivities));

        // Act
        List<ActivityResponse> result = activityService.getAllActivities();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(webClient, times(1)).get();
    }
}
