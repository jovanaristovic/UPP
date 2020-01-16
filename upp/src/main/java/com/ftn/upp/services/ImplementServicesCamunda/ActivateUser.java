package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.ActivateAccountDto;
import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.UserRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivateUser implements JavaDelegate {

    @Autowired
    private UserRepository userRepository;
    @Override
    public void execute(DelegateExecution execution) throws Exception {

            List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration");
            System.out.println(registration);
            FormSubmissionDto usernameDTO = null;
            for (FormSubmissionDto dto : registration) {
                if (dto.getFieldId().equals("email")) {
                    usernameDTO = dto;
                }
            }

            User user =  userRepository.findUserByEmail(usernameDTO.getFieldValue());
            user.setEnabled(true);
            this.userRepository.save(user);


    }
}
