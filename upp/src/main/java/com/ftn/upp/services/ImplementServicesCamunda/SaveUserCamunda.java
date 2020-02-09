package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormSubmissionDto;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveUserCamunda implements JavaDelegate {

    @Autowired
    private IdentityService identityService;
    @Override
    public void execute(DelegateExecution execution) throws Exception {

        List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration");

        String username = "";
        User user = identityService.newUser("1");
        for (FormSubmissionDto formField : registration) {
            if(formField.getFieldId().equals("korisnickoIme")) {
                user.setId(formField.getFieldValue());
                username = formField.getFieldValue();
            }
            if(formField.getFieldId().equals("sifra")) {
                user.setPassword(formField.getFieldValue());
            }
        }

        List<User> users = identityService.createUserQuery().userId(username).list();
        if(users.isEmpty()) {
            identityService.saveUser(user);
        }
    }
}
