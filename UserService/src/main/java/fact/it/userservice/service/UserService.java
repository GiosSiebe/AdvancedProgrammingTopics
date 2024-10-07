package fact.it.userservice.service;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.model.User;
import fact.it.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .firstname(userRequest.getFirstname())
                .lastname(userRequest.getLastname())
                .build();

        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public void updateUser(String id, UserRequest userRequest) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(userRequest.getUsername());
            user.setPassword(userRequest.getPassword());
            user.setFirstname(userRequest.getFirstname());
            user.setLastname(userRequest.getLastname());

            userRepository.save(user);
        } else {
            throw new RuntimeException("User with ID " + id + " not found");
        }
    }

    public void deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with ID " + id + " not found");
        }
    }
}
