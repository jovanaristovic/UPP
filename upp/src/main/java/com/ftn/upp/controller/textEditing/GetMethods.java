package com.ftn.upp.controller.textEditing;

import com.ftn.upp.dto.FormFieldsDto;
import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.dto.JournalDto;
import com.ftn.upp.dto.ScientificFieldDto;
import com.ftn.upp.model.Journal;
import com.ftn.upp.model.ScientificField;
import com.ftn.upp.repository.ScienceFieldRepository;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.services.JournalService;
import com.ftn.upp.services.UserService;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("api/get")
public class GetMethods {

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


    @GetMapping(path = "/start/process/{username}", produces = "application/json")
    public @ResponseBody
    FormFieldsDto startProcess(@PathVariable String username) {

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("obradaPodnetogTeksta");

//        List<Journal> journalList = this.journalService.findAllJournals();
//        runtimeService.setVariable(pi.getId(), "lista_casopisa", journalList);

        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }
        if(!username.equals("nema")) {
            FormSubmissionDto taskFormData = new FormSubmissionDto("username", username);
            runtimeService.setVariable(task.getProcessInstanceId(), "username", taskFormData);
        }

        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }


    @GetMapping(value = "/journals/{processInstanceId}")
    public ResponseEntity<List<JournalDto>> getJournals(@PathVariable String processInstanceId){

        List<Journal> journals = (List<Journal>) runtimeService.getVariable(processInstanceId,"journals");

        List<JournalDto> journalDtos = new ArrayList<>();
        for(Journal m: journals){
            JournalDto journalDto = new JournalDto(m.getId(), m.getTitle());
            journalDtos.add(journalDto);
        }

        return new ResponseEntity(journalDtos, HttpStatus.OK);
    }


    @GetMapping(path = "/next/{procesInstanceId}", produces = "application/json")
    public @ResponseBody
    FormFieldsDto getNext(@PathVariable String procesInstanceId) {

        if( (taskService.createTaskQuery().processInstanceId(procesInstanceId).taskCandidateGroup("autor").list().isEmpty())){
            return null;

        }
        Task task = taskService.createTaskQuery().processInstanceId(procesInstanceId).taskCandidateGroup("autor").list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), procesInstanceId, properties);
    }

    @GetMapping(path = "/next/ispravljenje/podataka/{procesInstanceId}", produces = "application/json")
    public @ResponseBody
    FormFieldsDto getNextIspravljanjePodataka(@PathVariable String procesInstanceId) {

        procesInstanceId = procesInstanceId.substring(1, procesInstanceId.length() - 1);

        if( (taskService.createTaskQuery().processInstanceId(procesInstanceId).taskCandidateGroup("autor").list().isEmpty())){
            return null;

        }
        Task task = taskService.createTaskQuery().processInstanceId(procesInstanceId).taskCandidateGroup("autor").list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), procesInstanceId, properties);
    }

    @GetMapping(path = "/next/podproces/{procesInstanceId}", produces = "application/json")
    public @ResponseBody
    FormFieldsDto getNextPodproces(@PathVariable String procesInstanceId) {


        if( (taskService.createTaskQuery().processInstanceId(procesInstanceId).list().isEmpty())){
            return null;

        }
        Task task = taskService.createTaskQuery().processInstanceId(procesInstanceId).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), procesInstanceId, properties);
    }



    @GetMapping(value = "/scientificFields/{processInstanceId}")
    public ResponseEntity<List<ScientificFieldDto>> getScientificFields(@PathVariable String processInstanceId){

        List<ScientificField> scientificFields = (List<ScientificField>) runtimeService.getVariable(processInstanceId,"scientificFields");

        List<ScientificFieldDto> scientificFieldDtos = new ArrayList<>();
        for(ScientificField m: scientificFields){
            ScientificFieldDto scientificFieldDto = new ScientificFieldDto(m.getId(), m.getName());
            scientificFieldDtos.add(scientificFieldDto);
        }

        return new ResponseEntity(scientificFieldDtos, HttpStatus.OK);
    }

    @GetMapping(path = "/main/redactor/{processInstanceId}", produces = "application/json")
    public @ResponseBody
    FormFieldsDto getUrednik( @PathVariable String processInstanceId) {

        processInstanceId = processInstanceId.substring(1, processInstanceId.length() - 1);
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskCandidateGroup("urednik").list().get(0);


        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }


        return new FormFieldsDto(task.getId(), task.getProcessInstanceId(), properties);
    }
}
