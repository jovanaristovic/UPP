package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.User;
import com.ftn.upp.services.UserService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MempershipActive implements JavaDelegate {

    @Autowired
    private IdentityService identityService;

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        FormSubmissionDto usernameDto = (FormSubmissionDto) delegateExecution.getVariable("username");
        String username = usernameDto.getFieldValue();

        User user = this.userService.findUserByUsername(username);
        boolean isActive = user.getMempership().isActive();
        String isActiveString = "";
        if(isActive){
            isActiveString = "true";

        } else {
            isActiveString = "false";
        }


        delegateExecution.setVariable("clanarinaAktivna", isActiveString, delegateExecution.getCurrentActivityId());
        String sg = isActiveString;
    }
}
