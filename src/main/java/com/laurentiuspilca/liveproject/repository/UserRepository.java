package com.laurentiuspilca.liveproject.repository;

import com.laurentiuspilca.liveproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findUserByUsername(String username);
}
