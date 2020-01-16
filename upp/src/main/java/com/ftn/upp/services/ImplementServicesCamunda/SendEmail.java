package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.repository.UserRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendEmail implements JavaDelegate {

    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    public SendEmail(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception, MailException {
     //postaviti polje za aktivnog usera na false

        List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration");
        System.out.println(registration);
        FormSubmissionDto usernameDTO = null;
        for (FormSubmissionDto dto : registration) {
            if (dto.getFieldId().equals("email")) {
                usernameDTO = dto;
            }
        }

        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(usernameDTO.getFieldValue());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Aktivacija naloga");

        String url="http://localhost:4200/activate/" + usernameDTO.getFieldValue();
        mail.setText("Vas nalog ce biti aktiviran klikom na sledeci link:" + " " + url);
 //       javaMailSender.send(mail);

        System.out.println("Email poslat!");
    }
}
