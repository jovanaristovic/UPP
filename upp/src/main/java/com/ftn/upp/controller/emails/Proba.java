package com.ftn.upp.controller.emails;


import com.ftn.upp.dto.email.EmailDto;

import com.ftn.upp.dto.email.EmailDtoDrugi;
import com.ftn.upp.dto.email.EmailPdfDto;
import com.ftn.upp.services.JournalService;
import com.ftn.upp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;


@RestController
@RequestMapping(value = "api/proba")
public class Proba {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalService journalService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;



    @PostMapping(value = "/1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity prvi(@RequestBody EmailDto emailDto){
        System.out.println("Slanje emaila...");

        SimpleMailMessage mailRedactor = new SimpleMailMessage();
        mailRedactor.setTo(emailDto.getEmail());
        mailRedactor.setFrom(env.getProperty("spring.mail.username"));
        mailRedactor.setSubject("Prijavljen je novi rad u sistem");

        mailRedactor.setText("Novi rad je prijavljen. Potrebno ga je pregledati" );
        javaMailSender.send(mailRedactor);



        System.out.println("Email da je novi rad prijavljen poslat!");
        return new ResponseEntity<>( HttpStatus.OK);
    }


    @PostMapping(value = "/2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity drugi(@RequestBody EmailDtoDrugi emailDto){
        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(emailDto.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Odbijanje rada");

        mail.setText(emailDto.getPoruka());
//        javaMailSender.send(mail);
        System.out.println("Email rad nije relevantan poslat!");
        return new ResponseEntity<>( HttpStatus.OK);
    }

    public void writePdf(String fileName, byte[] decodedBytes) throws Exception {
        File file = new File("src/main/pdf/" + fileName);
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(decodedBytes);
        fop.flush();
        fop.close();
    }



    @PostMapping(value = "/3", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity treci(@RequestBody EmailPdfDto emailDto) throws IOException {

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decodedBytes = decoder.decodeBuffer(emailDto.getFile());

        String fileName = emailDto.getFileName();
        String email = emailDto.getEmail();

        System.out.println("Slanje emaila...");
        String content = "Odlucili ste da je rad tematski prihvatljiv, sada je neophodno pregledati pdf i odluciti da li je dobro formatiran!";

        ByteArrayOutputStream outputStream = null;

        try {
            //construct the text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(content);

            //now write the PDF content to the output stream
            writePdf(fileName, decodedBytes);

            //construct the pdf body part
            DataSource dataSource = new ByteArrayDataSource(decodedBytes, "application/pdf");
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));

            pdfBodyPart.setFileName(fileName);

            //construct the mime multi part
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            mimeMultipart.addBodyPart(pdfBodyPart);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            String htmlMsg = "<h3>Pozdrav "+email+",</h3><br> <p>Obavestavamo Vas da je neophodno da rad koji ste prilozili ispravite u najkracem mogucem roku!</p>";
            mimeMessage.setContent(mimeMultipart, "text/html");
            helper.setTo(email);
            helper.setSubject("Obavestenje o vracanju poslatog rada na doradu");
            helper.setFrom(env.getProperty("spring.mail.username"));
//            javaMailSender.send(mimeMessage);

            System.out.println("Email sa pdf-om poslat!");


        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            //clean off
            if(null != outputStream) {
                try { outputStream.close(); outputStream = null; }
                catch(Exception ex) { }
            }
        }
     return new ResponseEntity(HttpStatus.OK);
    }



    @PostMapping(path = "/4", produces = "application/json")
    public ResponseEntity postUploadFile(@RequestBody EmailPdfDto emailPdfDto)  throws IOException {


        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decodedBytes = decoder.decodeBuffer(emailPdfDto.getFile());

        File file = new File("src/main/pdf/" + emailPdfDto.getFileName());
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(decodedBytes);
        fop.flush();
        fop.close();

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }
}
