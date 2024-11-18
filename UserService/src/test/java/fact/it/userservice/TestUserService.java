package fact.it.userservice;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.model.User;
import fact.it.userservice.repository.UserRepository;
import fact.it.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestUserService {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        // Arrange
        UserRequest userRequest = UserRequest.builder()
                .username("testuser")
                .password("password123")
                .firstname("John")
                .lastname("Doe")
                .build();

        User user = User.builder()
                .id("1")
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .firstname(userRequest.getFirstname())
                .lastname(userRequest.getLastname())
                .build();

        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        userService.createUser(userRequest);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        User user1 = User.builder()
                .id("1")
                .username("user1")
                .password("password1")
                .firstname("John")
                .lastname("Doe")
                .build();

        User user2 = User.builder()
                .id("2")
                .username("user2")
                .password("password2")
                .firstname("Jane")
                .lastname("Smith")
                .build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<User> users = userService.getAllUsers();

        // Assert
        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        // Arrange
        String userId = "1";
        User user = User.builder()
                .id(userId)
                .username("testuser")
                .password("password123")
                .firstname("John")
                .lastname("Doe")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        String userId = "1";
        User existingUser = User.builder()
                .id(userId)
                .username("olduser")
                .password("oldpassword")
                .firstname("Old")
                .lastname("User")
                .build();

        UserRequest userRequest = UserRequest.builder()
                .username("newuser")
                .password("newpassword")
                .firstname("New")
                .lastname("User")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        userService.updateUser(userId, userRequest);

        // Assert
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
        assertEquals("newuser", existingUser.getUsername());
        assertEquals("New", existingUser.getFirstname());
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        String userId = "1";
        when(userRepository.existsById(userId)).thenReturn(true);

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testDeleteUserNotFound() {
        // Arrange
        String userId = "1";
        when(userRepository.existsById(userId)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deleteUser(userId));
        assertEquals("User with ID " + userId + " not found", exception.getMessage());
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteById(userId);
    }
}
