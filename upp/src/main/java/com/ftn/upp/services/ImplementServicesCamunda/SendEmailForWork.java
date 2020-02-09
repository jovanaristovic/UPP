package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.Authority;
import com.ftn.upp.model.Journal;
import com.ftn.upp.model.User;
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
public class SendEmailForWork implements JavaDelegate {


    @Autowired
    private UserService userService;

    @Autowired
    private JournalService journalService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormSubmissionDto> journal = (List<FormSubmissionDto>) delegateExecution.getVariable("choosenJournal");

        FormSubmissionDto titleDto = null;
        for (FormSubmissionDto dto : journal) {
            if (dto.getFieldId().equals("title")) {
                titleDto = dto;
            }
        }

        Journal journal1 = this.journalService.findJournalByTitle(titleDto.getFieldValue());
        User mainRed = null;
        List<User> users = journal1.getUsers();
        for(User u: users){
            List<Authority> authorities = (List<Authority>) u.getAuthorities();
            for(Authority a : authorities){
                if(a.getName().equals("MAIN_REDACTOR") ){
                    mainRed = u;
                }
            }
        }
        FormSubmissionDto usernameDto = (FormSubmissionDto) delegateExecution.getVariable("username");

        System.out.println("Slanje emaila...");

        SimpleMailMessage mailRedactor = new SimpleMailMessage();
        mailRedactor.setTo(mainRed.getUsername());
        mailRedactor.setFrom(env.getProperty("spring.mail.username"));
        mailRedactor.setSubject("Prijavljen je novi rad u sistem");

        mailRedactor.setText("Novi rad je prijavljen. Potrebno ga je pregledati" );
        //       javaMailSender.send(mailRedactor);

        SimpleMailMessage mailAuthor = new SimpleMailMessage();
        mailAuthor.setTo(usernameDto.getFieldValue());
        mailAuthor.setFrom(env.getProperty("spring.mail.username"));
        mailAuthor.setSubject("Rad je prijavljen");

        mailAuthor.setText("Uspesno ste prijavili rad!" );
        //       javaMailSender.send(mailAuthor);

        System.out.println("Email da je novi rad prijavljen poslat!");
    }
}
