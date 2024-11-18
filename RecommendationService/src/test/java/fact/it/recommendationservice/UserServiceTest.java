package fact.it.recommendationservice;

import fact.it.recommendationservice.dto.UserResponse;
import fact.it.recommendationservice.service.UserService;
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
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

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
        ReflectionTestUtils.setField(userService, "userServiceBaseUrl", "localhost:8082");
    }

    @Test
    public void testGetAllUsers_Success() {
        // Arrange
        UserResponse[] mockUsers = {
                new UserResponse("1", "user1", "password1", "John", "Doe"),
                new UserResponse("2", "user2", "password2", "Jane", "Doe")
        };

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(UserResponse[].class)).thenReturn(Mono.just(mockUsers));

        // Act
        List<UserResponse> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());

        verify(webClient, times(1)).get();
    }

    @Test
    public void testGetUserById_Success() {
        // Arrange
        String userId = "1";
        UserResponse mockUser = new UserResponse(userId, "user1", "password1", "John", "Doe");

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), eq(userId))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(UserResponse.class)).thenReturn(Mono.just(mockUser));

        // Act
        UserResponse result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals("user1", result.getUsername());
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());

        verify(webClient, times(1)).get();
    }

    @Test
    public void testGetUserById_UserNotFound() {
        // Arrange
        String userId = "nonexistentUserId";

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), eq(userId))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(UserResponse.class)).thenReturn(Mono.empty());

        // Act
        UserResponse result = userService.getUserById(userId);

        // Assert
        assertNull(result);
        verify(webClient, times(1)).get();
    }

    @Test
    public void testUserExists_True() {
        // Arrange
        String userId = "1";
        UserResponse mockUser = new UserResponse(userId, "user1", "password1", "John", "Doe");

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), eq(userId))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(UserResponse.class)).thenReturn(Mono.just(mockUser));

        // Act
        boolean result = userService.userExists(userId);

        // Assert
        assertTrue(result);
        verify(webClient, times(1)).get();
    }

    @Test
    public void testUserExists_False() {
        // Arrange
        String userId = "nonexistentUserId";

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), eq(userId))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(UserResponse.class)).thenReturn(Mono.empty());

        // Act
        boolean result = userService.userExists(userId);

        // Assert
        assertFalse(result);
        verify(webClient, times(1)).get();
    }
}
