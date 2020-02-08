package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.model.ScientificField;
import com.ftn.upp.services.ScientificFieldService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetScientificFields implements JavaDelegate {


    @Autowired
    private ScientificFieldService scientificFieldService;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

            String processInstanceId = delegateExecution.getProcessInstance().getId();
            List<ScientificField> scientificFields = this.scientificFieldService.findAll();
            this.runtimeService.setVariable(processInstanceId,"scientificFields", scientificFields);


        }
}

