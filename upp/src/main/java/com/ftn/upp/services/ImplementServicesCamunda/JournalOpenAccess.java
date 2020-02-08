package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormFieldsDto;
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
public class JournalOpenAccess implements JavaDelegate {


    @Autowired
    private JournalService journalService;

    @Autowired
    private RuntimeService runtimeService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormSubmissionDto> journal = (List<FormSubmissionDto>) delegateExecution.getVariable("choosenJournal");

        FormSubmissionDto journalNameDto = null;
        for (FormSubmissionDto dto : journal) {
            if (dto.getFieldId().equals("title")) {
                journalNameDto = dto;
            }
        }

        Journal journalInBase = this.journalService.findJournalByTitle(journalNameDto.getFieldValue());
        String isOpenAccessString = "";
        if(journalInBase.getPayment().equals("open access")){
            isOpenAccessString = "true";
        } else {
            isOpenAccessString = "false";
        }


//        FormSubmissionDto formFieldsDto = new FormSubmissionDto("isOpenAccess", isOpenAccessString);

        delegateExecution.setVariable("openAccess", isOpenAccessString, delegateExecution.getCurrentActivityId());

    }

}
