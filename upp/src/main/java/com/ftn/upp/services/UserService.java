package com.ftn.upp.services;

import com.ftn.upp.dto.ActivateAccountDto;
import com.ftn.upp.model.User;
import org.springframework.stereotype.Service;


@Service
public interface UserService {

    User findUserByUsername(String username);
    void sendMailForActivation(User user);
    User activateAccount(ActivateAccountDto activateAccountDto);

}
