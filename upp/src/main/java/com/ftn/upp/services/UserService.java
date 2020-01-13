package com.ftn.upp.services;

import com.ftn.upp.model.User;
import org.springframework.stereotype.Service;


@Service
public interface UserService {

    User findUserByUsername(String username);

}
