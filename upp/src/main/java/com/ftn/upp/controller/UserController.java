package com.ftn.upp.controller;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.dto.TaskDto;
import com.ftn.upp.model.Authority;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.AuthorityRepository;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.services.UserService;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value="/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private Environment env;


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/{username}")
    public User getUserByUsername(@PathVariable String username) {
        User user = userService.findUserByUsername(username);

        return user;
    }

    @PostMapping(value = "/register/{taskId}")
    public ResponseEntity<User> register (@RequestBody List<FormSubmissionDto> registrationData, @PathVariable("taskId") String taskId){

        HashMap<String,Object> map = mapListToDto(registrationData);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        FormSubmissionDto usernameDTO = null;
        for(FormSubmissionDto dto : registrationData){
            if(dto.getFieldId().equals("korisnickoIme")){
                usernameDTO = dto;
            }
        }

        User user = userService.findUserByUsername(usernameDTO.getFieldValue());
        if(user != null){
//            runtimeService.setVariable(processInstanceId, "dataValid", false);
            return ResponseEntity.notFound().build();
        }

        User newUser = new User(registrationData);
        Authority authority = authorityRepository.findAuthorityByName("ROLE_USER");
        List<Authority> authorities = Arrays.asList(authority);
        newUser.setAuthorities(authorities);
        for(FormSubmissionDto dto : registrationData) {
            if (dto.getFieldId().equals("sifra")) {
                newUser.setPassword(passwordEncoder.encode(dto.getFieldValue()));
            }
        }

        userRepository.save(newUser);

//        if(newUser.isReviewer()){
//            runtimeService.setVariable(processInstanceId, "isReviewer", true);
//        }else{
//            runtimeService.setVariable(processInstanceId, "isReviewer", false);
//        }


        runtimeService.setVariable(processInstanceId, "userId", newUser.getUsername());
        formService.submitTaskForm(taskId, map);
//        runtimeService.setVariable(processInstanceId, "dataValid", true);


        return new ResponseEntity<>(newUser,HttpStatus.CREATED);
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDto temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }

    @RequestMapping(value= "/get/tasks/scientificField", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<TaskDto>> getTaskScientificField(){
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("guest").list();
        List<TaskDto> tasksDto = new ArrayList<>();
        for(Task task: tasks){
            if(task.getName().equals("Naucne oblasti")) {
                TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
                tasksDto.add(t);
            }
        }
        return new ResponseEntity<>(tasksDto, HttpStatus.OK);
    }

    @PostMapping(value = "sendMail/{username}")
    public void sentEmail(@PathVariable String username) {
        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();
        username += ".com";
        mail.setTo(username);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Aktivacija naloga");

        String url="http://localhost:4200/activate/" + username;
        mail.setText("Vas nalog ce biti aktiviran klikom na sledeci link:" + " " + url);
//        javaMailSender.send(mail);

        System.out.println("Email poslat!");

    }

}
