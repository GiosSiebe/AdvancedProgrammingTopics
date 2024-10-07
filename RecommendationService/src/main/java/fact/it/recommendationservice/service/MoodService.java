package fact.it.recommendationservice.service;

import fact.it.recommendationservice.dto.MoodResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MoodService {

    private final WebClient webClient;

    @Value("${moodservice.baseurl}")
    private String moodServiceBaseUrl;

    public MoodService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://"+moodServiceBaseUrl+"/api/mood").build(); // Replace with the actual URL of the Mood service
    }

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
