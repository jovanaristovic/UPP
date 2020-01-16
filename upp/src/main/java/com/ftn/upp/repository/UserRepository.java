package com.ftn.upp.repository;

import com.ftn.upp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
}
