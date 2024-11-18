package fact.it.recommendationservice;

import fact.it.recommendationservice.dto.MoodResponse;
import fact.it.recommendationservice.service.MoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MoodServiceTest {

    @InjectMocks
    private MoodService moodService;

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
        ReflectionTestUtils.setField(moodService, "moodServiceBaseUrl", "localhost:8082");
    }

    @Test
    public void testGetAllMoods_Success() {
        // Arrange
        MoodResponse[] mockMoods = {
                MoodResponse.builder().id("1").userId("user1").mood("Happy").timestamp(LocalDateTime.now()).build(),
                MoodResponse.builder().id("2").userId("user2").mood("Sad").timestamp(LocalDateTime.now()).build()
        };

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(MoodResponse.class)).thenReturn(Flux.just(mockMoods));  // Correcting to use Flux.just()

        // Act
        List<MoodResponse> result = moodService.getAllMoods();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Happy", result.get(0).getMood());
        assertEquals("Sad", result.get(1).getMood());

        verify(webClient, times(1)).get();
    }


    @Test
    public void testGetAllMoods_EmptyResult() {
        // Arrange
        MoodResponse[] mockMoods = {};  // Empty result mock

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(MoodResponse.class)).thenReturn(Flux.empty());  // Return an empty flux

        // Act
        List<MoodResponse> result = moodService.getAllMoods();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(webClient, times(1)).get();
    }


    @Test
    public void testGetAllMoodsByTimestamp_Success() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        MoodResponse[] mockMoods = {
                MoodResponse.builder().id("1").userId("user1").mood("Happy").timestamp(timestamp).build(),
                MoodResponse.builder().id("2").userId("user2").mood("Sad").timestamp(timestamp).build()
        };

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), eq(timestamp))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(MoodResponse.class)).thenReturn(Flux.just(mockMoods)); // Ensure Flux is returned

        // Act
        List<MoodResponse> result = moodService.getAllMoodsByTimestamp(timestamp);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Happy", result.get(0).getMood());
        assertEquals("Sad", result.get(1).getMood());

        verify(webClient, times(1)).get();
    }

}
