package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.ScientificField;
import com.ftn.upp.model.User;
import com.ftn.upp.services.ScientificFieldService;
import com.ftn.upp.services.UserService;
import org.apache.catalina.LifecycleState;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IzborUrednikaNaucne implements JavaDelegate {


    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        FormSubmissionDto naucnaDto = (FormSubmissionDto) delegateExecution.getVariable("naucnaOblast");
        List<User> userList = this.userService.findAll();

        User urednikNaucneUser = null;
        for(User u: userList){
            for (ScientificField s: u.getScientificFields()){
                if(s.getName().equals(naucnaDto.getFieldValue())){

                    urednikNaucneUser = u;
                }
            }
        }

        delegateExecution.setVariable("urednikNaucneEmail", urednikNaucneUser.getEmail(), delegateExecution.getCurrentActivityId());

    }
}
