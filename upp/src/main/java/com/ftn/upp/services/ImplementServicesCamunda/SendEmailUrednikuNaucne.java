package com.ftn.upp.services.ImplementServicesCamunda;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailUrednikuNaucne implements JavaDelegate {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String urednikEmail = (String) delegateExecution.getVariable("urednikNaucneEmail");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(urednikEmail);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Prijavljen je novi rad u sistem");

        mail.setText("Pristigao je novi rad");
        //       javaMailSender.send(mailRedactor);

        System.out.println("Email uredniku naucne da je pristigao novi rad!");
    }
}
