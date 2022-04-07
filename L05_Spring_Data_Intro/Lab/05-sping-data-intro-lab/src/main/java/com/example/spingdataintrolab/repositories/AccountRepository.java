package com.example.spingdataintrolab.repositories;

import com.example.spingdataintrolab.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
