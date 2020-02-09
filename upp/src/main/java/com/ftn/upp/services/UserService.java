package com.ftn.upp.services;

import com.ftn.upp.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {

    User findUserByUsername(String username);
    void sendMailForActivation(User user);
    User findUserByEmail(String email);
    List<User> findAll();

}
