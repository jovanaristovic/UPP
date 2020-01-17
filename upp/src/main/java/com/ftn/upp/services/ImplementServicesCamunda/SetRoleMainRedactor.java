package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.Authority;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.AuthorityRepository;
import com.ftn.upp.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SetRoleMainRedactor implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        FormSubmissionDto redactorDto = (FormSubmissionDto) execution.getVariable("redactor");
        String redactorEmail = redactorDto.getFieldValue();
        redactorEmail += ".com";
        Authority authority = this.authorityRepository.findAuthorityByName("MAIN_REDACTOR");
        List<Authority> authorities = Arrays.asList(authority);

        User user = this.userService.findUserByUsername(redactorEmail);
        user.getAuthorities().removeAll(user.getAuthorities());
        user.setAuthorities(authorities);

    }
}
