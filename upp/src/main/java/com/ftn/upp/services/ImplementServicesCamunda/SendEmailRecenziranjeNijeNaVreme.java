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

import java.util.List;

@Service
public class SendEmailRecenziranjeNijeNaVreme implements JavaDelegate {


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


        FormSubmissionDto usernameDto = (FormSubmissionDto) delegateExecution.getVariable("username");
        List<FormSubmissionDto> pregedanPdf = (List<FormSubmissionDto>) delegateExecution.getVariable("pregledanPdf");

        User user = this.userService.findUserByUsername(usernameDto.getFieldValue());



        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Istekao rok za recenziju");

        mail.setText("Niste na vreme odradili recenziu!");
        //       javaMailSender.send(mailRedactor);

        System.out.println("Email Recenzija nije odradjena na vreme!");
    }
}
