package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.Journal;
import com.ftn.upp.services.JournalService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GetJournals implements JavaDelegate {

    @Autowired
    private JournalService journalService;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String processInstanceId = execution.getProcessInstance().getId();
        List<Journal> journals = this.journalService.findAllJournals();
        this.runtimeService.setVariable(processInstanceId,"journals", journals);


    }
}
