package fact.it.recommendationservice;

import fact.it.recommendationservice.dto.MoodResponse;
import fact.it.recommendationservice.service.MoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class MoodServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private ResponseSpec responseSpec;

    @InjectMocks
    private MoodService moodService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(webClientBuilder.baseUrl(any())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
    }

    @Test
    void testGetAllMoodsByTimestamp() {
        // Mock the WebClient behavior
        MoodResponse mood1 = new MoodResponse(); // Initialize with your properties
        MoodResponse mood2 = new MoodResponse(); // Initialize with your properties

        // Mock the method calls
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(MoodResponse.class)).thenReturn(Flux.just(mood1, mood2));

        List<MoodResponse> moods = moodService.getAllMoodsByTimestamp(LocalDateTime.now());

        assertEquals(2, moods.size());
    }

    @Test
    void testGetAllMoods() {
        // Mock the WebClient behavior
        MoodResponse mood1 = new MoodResponse(); // Initialize with your properties
        MoodResponse mood2 = new MoodResponse(); // Initialize with your properties

        // Mock the method calls
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(MoodResponse.class)).thenReturn(Flux.just(mood1, mood2));

        List<MoodResponse> moods = moodService.getAllMoods();

        assertEquals(2, moods.size());
    }
}
