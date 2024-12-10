package za.co.marlonmagonjo.medici.service.impl;

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

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception ex) {
            throw new UserCreationException("Failed to create user.", ex);
        }
    }

    @Override
    public Optional<User> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new GetUserByIdException("User with ID " + id + " not found.");
        }
        return user;
    }


    @Override
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword());
                    try {
                        return userRepository.save(user);
                    } catch (Exception ex) {
                        throw new UserUpdateException("Failed to update user.", ex);
                    }
                })
                .orElseThrow(() -> new UserUpdateException("User not found for update."));
    }

    @Override
    public void deleteUser(Long id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
            } else {
                throw new UserDeletionException("User not found for deletion.");
            }
        } catch (Exception ex) {
            throw new UserDeletionException("Failed to delete user. Please try again.", ex);
        }
    }
}
