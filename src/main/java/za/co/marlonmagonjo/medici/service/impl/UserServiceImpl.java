package za.co.marlonmagonjo.medici.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.marlonmagonjo.medici.exception.GetUserByIdException;
import za.co.marlonmagonjo.medici.exception.UserCreationException;
import za.co.marlonmagonjo.medici.exception.UserDeletionException;
import za.co.marlonmagonjo.medici.exception.UserUpdateException;
import za.co.marlonmagonjo.medici.model.User;
import za.co.marlonmagonjo.medici.repos.UserRepository;
import za.co.marlonmagonjo.medici.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        logger.info("UserServiceImpl initialized with UserRepository");
    }

    @Override
    public User createUser(User user) {
        logger.info("Attempting to create user: {}", user.getUsername());
        if (userRepository.existsByUsername(user.getUsername())) {
            logger.warn("Username '{}' is already taken", user.getUsername());
            throw new UserCreationException("Username '" + user.getUsername() + "' is already taken.");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("Email '{}' is already registered", user.getEmail());
            throw new UserCreationException("Email '" + user.getEmail() + "' is already registered.");
        }

        try {
            User createdUser = userRepository.save(user);
            logger.info("User created successfully: {}", createdUser.getUsername());
            return createdUser;
        } catch (Exception ex) {
            logger.error("Failed to create user", ex);
            throw new UserCreationException("Failed to create user.", ex);
        }
    }

    @Override
    public Optional<User> getUserById(Long id) {
        logger.info("Attempting to get user by ID: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            logger.warn("User with ID {} not found", id);
            throw new GetUserByIdException("User with ID " + id + " not found.");
        }
        logger.info("User found: {}", user.get().getUsername());
        return user;
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        logger.info("Attempting to update user with ID: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User with ID {} not found for update", id);
                    return new UserUpdateException("User with ID " + id + " not found.");
                });

        if (!existingUser.getUsername().equals(updatedUser.getUsername()) &&
                userRepository.existsByUsername(updatedUser.getUsername())) {
            logger.warn("Username '{}' is already taken", updatedUser.getUsername());
            throw new UserUpdateException("Username '" + updatedUser.getUsername() + "' is already taken.");
        }

        if (!existingUser.getEmail().equals(updatedUser.getEmail()) &&
                userRepository.existsByEmail(updatedUser.getEmail())) {
            logger.warn("Email '{}' is already registered", updatedUser.getEmail());
            throw new UserUpdateException("Email '" + updatedUser.getEmail() + "' is already registered.");
        }

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());

        try {
            User updated = userRepository.save(existingUser);
            logger.info("User updated successfully: {}", updated.getUsername());
            return updated;
        } catch (Exception ex) {
            logger.error("Failed to update user", ex);
            throw new UserUpdateException("Failed to update user.", ex);
        }
    }

    @Override
    public void deleteUser(Long id) {
        logger.info("Attempting to delete user with ID: {}", id);
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                logger.info("User with ID {} deleted successfully", id);
            } else {
                logger.warn("User with ID {} not found for deletion", id);
                throw new UserDeletionException("User not found for deletion.");
            }
        } catch (Exception ex) {
            logger.error("Failed to delete user with ID: {}", id, ex);
            throw new UserDeletionException("Failed to delete user. Please try again.", ex);
        }
    }
}