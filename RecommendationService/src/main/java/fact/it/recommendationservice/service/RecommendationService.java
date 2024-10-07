package fact.it.recommendationservice.service;

import fact.it.recommendationservice.dto.ActivityResponse;
import fact.it.recommendationservice.dto.MoodResponse;
import fact.it.recommendationservice.dto.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final WebClient webClient;
    private final UserService userService;

    @Value("${userservice.baseurl}")
    private String userServiceBaseUrl;

    @Value("${activityservice.baseurl}")
    private String activityServiceBaseUrl;

    public List<RecommendationResponse> getRecommendationsByUserId(String userId) {
        // Check if user exists
        if (userService.userExists(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        List<MoodResponse> moodResponses; // Declare the variable outside the try block

        try {
            // Fetch all moods from the mood microservice
            moodResponses = webClient.get()
                    .uri("http://"+userServiceBaseUrl+"/api/mood/user/" + userId)
                    .retrieve()
                    .bodyToFlux(MoodResponse.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            // Handle specific HTTP errors
            throw new ResponseStatusException(e.getStatusCode(), "Error fetching moods", e);
        }

        // Check if moodResponses is null or empty
        if (moodResponses == null || moodResponses.isEmpty()) {
            return List.of(); // Return empty list if no mood responses are found
        }

        List<ActivityResponse> activities; // Declare activities variable

        try {
            // Fetch activities from the activity microservice
            activities = webClient.get()
                    .uri("http://"+activityServiceBaseUrl+"/api/activity") // Update the URL as needed
                    .retrieve()
                    .bodyToFlux(ActivityResponse.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            // Handle specific HTTP errors
            throw new ResponseStatusException(e.getStatusCode(), "Error fetching activities", e);
        }

        // Generate recommendations based on moods and activities
        List<RecommendationResponse> recommendations = new ArrayList<>();
        for (MoodResponse moodResponse : moodResponses) {
            recommendations.addAll(activities.stream()
                    .filter(activity -> activity.getCategory() != null && // Ensure category is not null
                            activity.getCategory().equals(moodResponse.getMood())) // Ensure proper comparison
                    .map(activity -> RecommendationResponse.builder()
                            .userId(userId) // Set the userId
                            .mood(moodResponse) // Include the mood response
                            .activity(activity) // Include the activity response
                            .timestamp(moodResponse.getTimestamp()) // Use the mood's timestamp
                            .build())
                    .collect(Collectors.toList()));
        }

        return recommendations;
    }


}

