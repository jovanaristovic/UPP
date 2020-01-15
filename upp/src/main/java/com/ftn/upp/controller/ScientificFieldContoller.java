package com.ftn.upp.controller;

import com.ftn.upp.dto.FormFieldsDto;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/scientificField")
public class ScientificFieldContoller {


    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @RequestMapping(value = "/get/form/{taskId}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> getFormFields(@PathVariable("taskId") String taskId){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        return new ResponseEntity<>(new FormFieldsDto(task.getId(), processInstanceId, properties), HttpStatus.OK);
    }

}
