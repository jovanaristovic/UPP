package com.ftn.upp.services.serviceImpl;

import com.ftn.upp.dto.ActivateAccountDto;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
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
    public void sendMailForActivation(User user) throws MailException {

        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Aktivacija naloga");

        String url="http://localhost:4200/activate/" + user.getEmail();
        mail.setText("Vas nalog ce biti aktiviran klikom na sledeci link:" + " " + url);
        javaMailSender.send(mail);

        System.out.println("Email poslat!");
    }

    @Override
    public User activateAccount(ActivateAccountDto activateAccountDto){

        User user =  userRepository.findUserByUsername(activateAccountDto.getEmail());
        user.setEnabled(true);
        return this.userRepository.save(user);

    }


}
