package za.co.marlonmagonjo.medici.repos;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.marlonmagonjo.medici.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
