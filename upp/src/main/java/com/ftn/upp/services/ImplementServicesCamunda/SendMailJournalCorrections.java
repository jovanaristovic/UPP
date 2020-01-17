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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendMailJournalCorrections implements JavaDelegate {

    @Autowired
    private Environment env;


    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception, MailException {


        FormSubmissionDto redactorDto = (FormSubmissionDto) delegateExecution.getVariable("redactor");
        String redactorEmail = redactorDto.getFieldValue();
        redactorEmail += ".com";
        List<FormSubmissionDto> correctedData = (List<FormSubmissionDto>) delegateExecution.getVariable("ispravljeniPodaciOCasopisuAdmin");


        FormSubmissionDto komentarDto = null;
        for (FormSubmissionDto dto : correctedData) {
            if (dto.getFieldId().equals("kometar")) {
                komentarDto = dto;
            }
        }


        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(redactorEmail);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Ispravka podataka o casopisu");
        if(komentarDto.getFieldValue().isEmpty()){
            mail.setText("neispravni podaci o casopisu");
        }
        else {
            mail.setText(komentarDto.getFieldValue());
        }
        //       javaMailSender.send(mail);

        System.out.println("Email poslat!");

    }
}
