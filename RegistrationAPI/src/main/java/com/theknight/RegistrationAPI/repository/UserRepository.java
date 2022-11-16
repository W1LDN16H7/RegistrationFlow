package com.theknight.RegistrationAPI.repository;

import com.theknight.RegistrationAPI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
