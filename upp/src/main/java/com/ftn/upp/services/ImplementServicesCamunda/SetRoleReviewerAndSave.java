package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.Authority;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.AuthorityRepository;
import com.ftn.upp.repository.UserRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SetRoleReviewerAndSave implements JavaDelegate {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration");
        System.out.println(registration);
        FormSubmissionDto usernameDTO = null;
        for (FormSubmissionDto dto : registration) {
            if (dto.getFieldId().equals("korisnickoIme")) {
                usernameDTO = dto;
            }
        }

        User user =  userRepository.findUserByUsername(usernameDTO.getFieldValue());
        Authority authority = authorityRepository.findAuthorityByName("REVIEWER");
//        List<Authority> authorities = Arrays.asList(authority);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        user.setReviewer(true);
        this.userRepository.save(user);

    }
}
