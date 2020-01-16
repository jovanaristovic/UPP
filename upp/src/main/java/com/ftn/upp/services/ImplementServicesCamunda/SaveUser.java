package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.Authority;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.AuthorityRepository;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.services.UserService;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class SaveUser implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration");


        HashMap<String, Object> map = mapListToDto(registration);


        FormSubmissionDto usernameDTO = null;
        for (FormSubmissionDto dto : registration) {
            if (dto.getFieldId().equals("korisnickoIme")) {
                usernameDTO = dto;
            }
        }

        com.ftn.upp.model.User user = userService.findUserByUsername(usernameDTO.getFieldValue());
        if (user == null) {



        com.ftn.upp.model.User newUser = new User(registration);
        Authority authority = authorityRepository.findAuthorityByName("ROLE_USER");
        List<Authority> authorities = Arrays.asList(authority);
        newUser.setAuthorities(authorities);
        for (FormSubmissionDto dto : registration) {
            if (dto.getFieldId().equals("sifra")) {
                newUser.setPassword(passwordEncoder.encode(dto.getFieldValue()));
            }
        }

        userRepository.save(newUser);
        }

    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDto temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}

