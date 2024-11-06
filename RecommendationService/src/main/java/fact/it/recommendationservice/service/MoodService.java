package fact.it.recommendationservice.service;

import fact.it.recommendationservice.dto.MoodResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MoodService {
    private final WebClient webClient;

    @Value("${moodservice.baseurl}")
    private String moodServiceBaseUrl;

    public List<MoodResponse> getAllMoodsByTimestamp(LocalDateTime timestamp) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("timestamp", timestamp)
                        .build())
                .retrieve()
                .bodyToFlux(MoodResponse.class)
                .collectList()
                .block();
    }

    public List<MoodResponse> getAllMoods() {
        return webClient.get()
                .uri("/all")
                .retrieve()
                .bodyToFlux(MoodResponse.class)
                .collectList()
                .block();
    }

}
