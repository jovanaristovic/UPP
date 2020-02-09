package com.ftn.upp.services.serviceImpl;

import com.ftn.upp.dto.ActivateAccountDto;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;


    public User getUserById(Long id) {
        User user =  this.userRepository.findUserById(id);
        return user;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        User u = userRepository.findUserByUsername(username);
        return u;
    }
    @Override
    public User findUserByEmail(String email) {
        User u = userRepository.findUserByEmail(email);
        return u;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public void sendMailForActivation(User user) throws MailException {

    }




}
