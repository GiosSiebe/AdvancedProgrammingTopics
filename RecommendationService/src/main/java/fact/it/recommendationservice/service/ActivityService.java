package fact.it.recommendationservice.service;

import fact.it.recommendationservice.dto.ActivityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final WebClient webClient;

    @Value("${activityservice.baseurl}")
    private String activityServiceBaseUrl;


    // Fetch all activities
    public List<ActivityResponse> getAllActivities() {
        ActivityResponse[] activities = webClient.get()
                .uri("http://"+ activityServiceBaseUrl+ "/api/activity") // Endpoint to get all activities
                .retrieve()
                .bodyToMono(ActivityResponse[].class)
                .block(); // Block for synchronous call

        return List.of(activities);
    }

    // Fetch activities by category
    public List<ActivityResponse> findByCategory(String category) {
        ActivityResponse[] activities = webClient.get()
                .uri("http://"+ activityServiceBaseUrl + "/api/activity/category/{category}", category) // Endpoint to find by category
                .retrieve()
                .bodyToMono(ActivityResponse[].class)
                .block();

        return List.of(activities);
    }

}
