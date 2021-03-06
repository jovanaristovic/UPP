package com.ftn.upp.services.ImplementServicesCamunda;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.jws.soap.SOAPBinding;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.User;
import com.ftn.upp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Service
public class SendEmailWithPDF implements JavaDelegate {


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws MailException, InterruptedException, MessagingException {

        byte[] decodedBytes = (byte[]) delegateExecution.getVariable("pdfRad");
        String fileName = (String) delegateExecution.getVariable("pdfName");
        FormSubmissionDto usernameDto = (FormSubmissionDto) delegateExecution.getVariable("username");

        User user = this.userService.findUserByUsername(usernameDto.getFieldValue());


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
            String htmlMsg = "<h3>Pozdrav "+user.getName()+",</h3><br> <p>Obavestavamo Vas da je neophodno da rad koji ste prilozili ispravite u najkracem mogucem roku!</p>";
            mimeMessage.setContent(mimeMultipart, "text/html");
            helper.setTo(user.getEmail());
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
    }

    public void writePdf(String fileName, byte[] decodedBytes) throws Exception {
        File file = new File("src/main/pdf/" + fileName);
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(decodedBytes);
        fop.flush();
        fop.close();
    }
}
