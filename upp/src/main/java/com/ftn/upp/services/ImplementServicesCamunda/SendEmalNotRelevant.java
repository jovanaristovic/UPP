package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.services.JournalService;
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
public class SendEmalNotRelevant implements JavaDelegate {


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormSubmissionDto> workReview = (List<FormSubmissionDto>) delegateExecution.getVariable("pregledanRad");
        FormSubmissionDto usernameDto = (FormSubmissionDto) delegateExecution.getVariable("username");


        FormSubmissionDto workDto = null;
        for (FormSubmissionDto dto : workReview) {
            if (dto.getFieldId().equals("komentar_nije_relevantan")) {
                workDto = dto;
            }
        }

        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(usernameDto.getFieldValue());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Odbijanje rada");

        mail.setText(workDto.getFieldValue());
//        javaMailSender.send(mail);
        System.out.println("Email rad nije relevantan poslat!");



    }
}
