package com.ftn.upp.controller.textEditing;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.Authority;
import com.ftn.upp.model.Journal;
import com.ftn.upp.model.User;
import com.ftn.upp.model.Work;
import com.ftn.upp.repository.AuthorityRepository;
import com.ftn.upp.repository.ScienceFieldRepository;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.repository.WorkRepository;
import com.ftn.upp.services.JournalService;
import com.ftn.upp.services.UserService;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("api/post")
public class PostMethods {

    @Autowired
    IdentityService identityService;       // bavi se userima i grupama

    @Autowired
    private RuntimeService runtimeService;  // bavi pokrenutim procesima

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;


    @Autowired
    private ScienceFieldRepository scienceFieldRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalService journalService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private AuthorityRepository authorityRepository;


    @PostMapping(path = "/choose/journal/{taskId}")
    public @ResponseBody
    ResponseEntity chooseJournal(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId, "choosenJournal", dto);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }




    @PostMapping(path = "/pay/{taskId}")
    public @ResponseBody
    ResponseEntity payMembership(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);

        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @PostMapping(path = "/new/work/{taskId}")
    public @ResponseBody
    ResponseEntity newWork(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();


        runtimeService.setVariable(processInstanceId, "newWork", dto);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/add/koautor/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postAddingRedactor(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        List<FormSubmissionDto> newWork = (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId,"newWork");

        FormSubmissionDto naslovDto  = null;
        for(FormSubmissionDto dtoN : newWork) {
            if (dtoN.getFieldId().equals("naslov")) {
                naslovDto = dtoN;
            }

        }

        Work work = this.workRepository.findWorkByTitle(naslovDto.getFieldValue());
        User user = new User();
        for(FormSubmissionDto dto1 : dto) {

            if (dto1.getFieldId().equals("imeKoautora")) {
                user.setName(dto1.getFieldValue());
            } else if (dto1.getFieldId().equals("prezimeKoautora")) {
                user.setLastName(dto1.getFieldValue()) ;
            } else if (dto1.getFieldId().equals("emailKoautora")) {
                user.setEmail(dto1.getFieldValue());
                user.setUsername(dto1.getFieldValue());
            } else if (dto1.getFieldId().equals("gradKoautora")) {
                user.setCity(dto1.getFieldValue());
            } else if (dto1.getFieldId().equals("drzavaKoautora")) {
                user.setCountry(dto1.getFieldValue());

            }

        }
        List<Authority> authorities = new ArrayList<>();
        Authority authority = new Authority();
        authority.setName("KOAUTOR");
        this.authorityRepository.save(authority);
        authorities.add(authority);

        user.setAuthorities(authorities);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode("123"));
        if(work.getUsers() == null){
            List<User> users = new ArrayList<>();
            users.add(user);
            work.setUsers(users);
        } else {
            work.getUsers().add(user);
        }

        userRepository.save(user);


        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
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
