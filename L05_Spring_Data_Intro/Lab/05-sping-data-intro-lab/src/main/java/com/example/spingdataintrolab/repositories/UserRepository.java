package com.example.spingdataintrolab.repositories;

import com.example.spingdataintrolab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
