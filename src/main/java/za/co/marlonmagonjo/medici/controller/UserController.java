package za.co.marlonmagonjo.medici.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.marlonmagonjo.medici.model.User;
import za.co.marlonmagonjo.medici.service.UserService;

import java.util.Optional;

/**
 * Controller for managing users.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "User", description = "API for managing user accounts")
public class UserController {

    private final UserService userService;

    /**
     * Constructor to initialize the service.
     *
     * @param userService The service layer for managing users.
     */
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user.
     *
     * @param user The user object to be created.
     * @return The created {@link User}.
     */
    @PostMapping("/")
    @Operation(summary = "Create User", description = "Creates a new user.")
    public ResponseEntity<User> createUser(
            @RequestBody @Parameter(description = "User object to be created.") User user) {
        return ResponseEntity.ok(this.userService.createUser(user));
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The {@link User} object if found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get User", description = "Retrieves a user by ID.")
    public ResponseEntity<Optional<User>> getUser(
            @PathVariable @Parameter(description = "ID of the user to retrieve.") Long id) {
        return ResponseEntity.ok(this.userService.getUserById(id));
    }

    /**
     * Updates an existing user.
     *
     * @param id   The ID of the user to update.
     * @param user The user object with updated information.
     * @return The updated {@link User}.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update User", description = "Updates an existing user.")
    public ResponseEntity<User> updateUser(
            @PathVariable @Parameter(description = "ID of the user to update.") Long id,
            @RequestBody @Parameter(description = "User object with updated information.") User user) {
        return ResponseEntity.ok(this.userService.updateUser(id, user));
    }

    /**
     * Deletes a user by ID.
     *
     * @param id The ID of the user to delete.
     * @return A response indicating successful deletion.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User", description = "Deletes a user by ID.")
    public ResponseEntity<Void> deleteUser(
            @PathVariable @Parameter(description = "ID of the user to delete.") Long id) {
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
