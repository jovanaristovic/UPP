package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.Journal;
import com.ftn.upp.services.JournalService;
import com.ftn.upp.services.UserService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class SaveJournal implements JavaDelegate {


    @Autowired
    private UserService userService;

    @Autowired
    IdentityService identityService;


    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    private JournalService journalService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        List<FormSubmissionDto> getJournal = (List<FormSubmissionDto>) execution.getVariable("newJournal");


        FormSubmissionDto titleDto = null;
        FormSubmissionDto ISSNDTO = null;
        for (FormSubmissionDto dto : getJournal) {
            if (dto.getFieldId().equals("naslov")) {
                titleDto = dto;
            }
            if (dto.getFieldId().equals("ISSN")) {
                ISSNDTO = dto;
            }
        }

        Journal journalExist = journalService.findJournalByTitle(titleDto.getFieldValue());
        Journal journalISSNEx = journalService.findJournalByISSN(ISSNDTO.getFieldValue());
        if (journalExist == null && journalISSNEx == null) {

           Journal journal = new Journal(getJournal);

            journalService.saveJournal(journal);
        }


    }
}
