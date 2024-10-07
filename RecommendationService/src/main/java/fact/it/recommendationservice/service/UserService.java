package fact.it.recommendationservice.service;

import fact.it.recommendationservice.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final WebClient webClient;

    @Value("${userservice.baseurl}")
    private String userServiceBaseUrl;

    // Fetch all users from the user-service through the API gateway
    public List<UserResponse> getAllUsers() {
        UserResponse[] users = webClient.get()
                .uri("http://"+userServiceBaseUrl+"/api/user") // Assuming gateway forwards to user-service
                .retrieve()
                .bodyToMono(UserResponse[].class)
                .block(); // Block for synchronous call

        return List.of(users);
    }

    // Fetch a specific user by ID
    public UserResponse getUserById(String userId) {
        return webClient.get()
                .uri("http://"+userServiceBaseUrl+"/api/user/{userId}", userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block(); // Block for synchronous call
    }

    public boolean userExists(String userId) {
        try {
            webClient.get()
                    .uri("http://"+userServiceBaseUrl+"/api/user/{userId}", userId)
                    .retrieve()
                    .bodyToMono(UserResponse.class) // Still use Mono to retrieve user info
                    .block(); // Blocking call here to return a boolean

            return true; // User exists
        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false; // User does not exist
            }
            throw new RuntimeException("Error occurred while checking user existence", ex); // Propagate other errors
        }
    }
}
