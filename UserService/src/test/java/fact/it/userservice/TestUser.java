package fact.it.userservice;

import fact.it.userservice.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getFirstname());
        assertNull(user.getLastname());
    }

    @Test
    void testAllArgsConstructor() {
        user = new User("1", "john_doe", "password123", "John", "Doe");

        assertEquals("1", user.getId());
        assertEquals("john_doe", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("John", user.getFirstname());
        assertEquals("Doe", user.getLastname());
    }

    @Test
    void testSettersAndGetters() {
        user.setId("2");
        user.setUsername("jane_doe");
        user.setPassword("securePassword");
        user.setFirstname("Jane");
        user.setLastname("Doe");

        assertEquals("2", user.getId());
        assertEquals("jane_doe", user.getUsername());
        assertEquals("securePassword", user.getPassword());
        assertEquals("Jane", user.getFirstname());
        assertEquals("Doe", user.getLastname());
    }

    @Test
    void testBuilder() {
        user = User.builder()
                .id("3")
                .username("bob_smith")
                .password("anotherPassword")
                .firstname("Bob")
                .lastname("Smith")
                .build();

        assertEquals("3", user.getId());
        assertEquals("bob_smith", user.getUsername());
        assertEquals("anotherPassword", user.getPassword());
        assertEquals("Bob", user.getFirstname());
        assertEquals("Smith", user.getLastname());
    }
}
