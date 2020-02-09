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

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Service
public class SendEmailCorrectionPdf implements JavaDelegate {



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

        FormSubmissionDto komentarDto = null;
        for (FormSubmissionDto dto : pregedanPdf) {
            if (dto.getFieldId().equals("komentar_za_pdf")) {
                komentarDto = dto;
            }
        }

        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Prijavljen je novi rad u sistem");

        mail.setText(komentarDto.getFieldValue());
        //       javaMailSender.send(mailRedactor);

        System.out.println("Email za ispravak pdf-a poslat!");


    }
}
