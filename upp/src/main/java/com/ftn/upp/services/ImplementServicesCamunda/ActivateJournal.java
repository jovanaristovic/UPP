package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.Journal;
import com.ftn.upp.services.JournalService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivateJournal implements JavaDelegate {

    @Autowired
    private JournalService journalService;
    @Override
    public void execute(DelegateExecution execution) throws Exception {

        List<FormSubmissionDto> journal = (List<FormSubmissionDto>) execution.getVariable("newJournal");

        FormSubmissionDto titleJournal = null;
        for (FormSubmissionDto dto : journal) {
            if (dto.getFieldId().equals("naslov")) {
                titleJournal = dto;
            }
        }
        String naslov = titleJournal.getFieldValue();
        Journal journal1 = this.journalService.findJournalByTitle(naslov);
        journal1.setActive(true);
        this.journalService.saveJournal(journal1);
    }
}
