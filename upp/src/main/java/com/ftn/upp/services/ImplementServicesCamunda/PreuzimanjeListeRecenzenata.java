package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.Authority;
import com.ftn.upp.model.Journal;
import com.ftn.upp.model.User;
import com.ftn.upp.services.JournalService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PreuzimanjeListeRecenzenata implements JavaDelegate {

    @Autowired
    private JournalService journalService;


    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        //recenzenti koji rade za obabrani casopis
        String processInstanceId = delegateExecution.getProcessInstance().getId();

        List<FormSubmissionDto> journalDto = (List<FormSubmissionDto>) delegateExecution.getVariable("choosenJournal");

        FormSubmissionDto journalTitleDto = null;
        for (FormSubmissionDto f : journalDto) {
            if (f.getFieldId().equals("title") ) {
                journalTitleDto = f;
            }
        }

        Journal journal = this.journalService.findJournalByTitle(journalTitleDto.getFieldValue());
        List<User> recenzenti = new ArrayList<>();

        for (User u : journal.getUsers()) {

            List<Authority> authorities = (List<Authority>) u.getAuthorities();
            for (Authority a : authorities) {
                if (a.getName().equals("REVIEWER")) {
                    recenzenti.add(u);
                }
            }

        }
        if(recenzenti.isEmpty()){
            this.runtimeService.setVariable(processInstanceId,"postoje_recenzenti", "false");

        } else {
            this.runtimeService.setVariable(processInstanceId,"postoje_recenzenti", "true");

        }
        this.runtimeService.setVariable(processInstanceId,"recenzenti", recenzenti);


    }
}
