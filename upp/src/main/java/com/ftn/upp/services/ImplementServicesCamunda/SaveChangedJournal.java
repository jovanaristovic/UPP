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

import java.util.HashMap;
import java.util.List;

@Service
public class SaveChangedJournal implements JavaDelegate {


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
    public void execute(DelegateExecution delegateExecution) throws Exception {


        List<FormSubmissionDto> getJournal = (List<FormSubmissionDto>) delegateExecution.getVariable("ispravljeniPodaciOCasopisuAdmin");


        FormSubmissionDto ISSNDTO = null;
        FormSubmissionDto nazivDTO = null;
        FormSubmissionDto nacinPlacanja = null;
        for (FormSubmissionDto dto : getJournal) {

            if (dto.getFieldId().equals("ISSN_izmena")) {
                ISSNDTO = dto;
            } else if(dto.getFieldId().equals("naslov_izmena")){
                nazivDTO = dto;
            } else if(dto.getFieldId().equals("nacinPlacanja_izmena")){
                nacinPlacanja = dto;
            }
        }

            Journal journal = this.journalService.findJournalByISSN(ISSNDTO.getFieldValue());
            journal.setPayment(nacinPlacanja.getFieldValue());
            journal.setTitle(nazivDTO.getFieldValue());


            journalService.saveJournal(journal);



    }


}

