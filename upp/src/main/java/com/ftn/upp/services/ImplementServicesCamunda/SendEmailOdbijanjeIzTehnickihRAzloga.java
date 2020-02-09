package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.User;
import com.ftn.upp.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailOdbijanjeIzTehnickihRAzloga implements JavaDelegate {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        FormSubmissionDto usernameDto = (FormSubmissionDto) delegateExecution.getVariable("username");
        User user = this.userService.findUserByUsername(usernameDto.getFieldValue());

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Prijavljen je novi rad u sistem");

        mail.setText("Vas rad je odbijen iz tehnickih razloga");
        //       javaMailSender.send(mailRedactor);

        System.out.println("Email odbijanje rada iz tehnickih razloga poslat!");

    }
}
