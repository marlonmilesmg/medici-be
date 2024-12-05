package za.co.marlonmagonjo.medici.service;

import za.co.marlonmagonjo.medici.model.User;

import java.util.Optional;

public interface UserService {
    User createUser(User user);

    Optional<User> getUserById(Long id);

    User updateUser(Long id, User updatedUser);

    void deleteUser(Long id);
}
