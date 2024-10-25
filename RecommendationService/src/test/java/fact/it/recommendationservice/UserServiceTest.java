package fact.it.recommendationservice;

import fact.it.recommendationservice.dto.UserResponse;
import fact.it.recommendationservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.Builder webClientBuilder; // Mock the WebClient builder

    @Mock
    private RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private ResponseSpec responseSpec;

    @InjectMocks
    private UserService userService;

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
    void testGetAllUsers() {
        // Mock the WebClient behavior
        UserResponse user1 = new UserResponse(); // Initialize with your properties
        UserResponse user2 = new UserResponse(); // Initialize with your properties
        UserResponse[] mockUsers = {user1, user2};

        when(responseSpec.bodyToMono(UserResponse[].class)).thenReturn(Mono.just(mockUsers));

        List<UserResponse> users = userService.getAllUsers();

        assertEquals(2, users.size());
    }

    @Test
    void testGetUserById() {
        // Mock the WebClient behavior
        UserResponse user = new UserResponse(); // Initialize with your properties

        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(responseSpec.bodyToMono(UserResponse.class)).thenReturn(Mono.just(user));

        UserResponse fetchedUser = userService.getUserById("userId");

        assertEquals(user, fetchedUser);
    }

    @Test
    void testUserExists_UserDoesNotExist() {
        // Mock the WebClient behavior for a non-existing user
        when(requestHeadersSpec.retrieve()).thenThrow(new WebClientResponseException(
                "Not Found",
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpHeaders.EMPTY,
                null,
                null));

        boolean exists = userService.userExists("unknownUserId");

        assertEquals(false, exists);
    }
}
