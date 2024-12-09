package za.co.marlonmagonjo.medici;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.marlonmagonjo.medici.model.User;
import za.co.marlonmagonjo.medici.repos.UserRepository;
import za.co.marlonmagonjo.medici.service.impl.UserServiceImpl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        User user = new User();
        user.setUsername("Test User");
        user.setEmail("test@example.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("Test User", createdUser.getUsername());
        assertEquals("test@example.com", createdUser.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("Test User");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(userId);

        assertTrue(foundUser.isPresent());
        assertEquals(userId, foundUser.get().getId());
        verify(userRepository).findById(userId);
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldReturnEmpty() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserById(userId);

        assertTrue(foundUser.isEmpty());
        verify(userRepository).findById(userId);
    }

    @Test
    void updateUser_WhenUserExists_ShouldReturnUpdatedUser() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("Old Name");

        User updatedUser = new User();
        updatedUser.setUsername("New Name");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals("New Name", result.getUsername());
        verify(userRepository).findById(userId);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void deleteUser_ShouldCallRepositoryDelete() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }
}

