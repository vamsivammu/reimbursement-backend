package io.mavq.reimbursementbackend.repository;

import io.mavq.reimbursementbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
