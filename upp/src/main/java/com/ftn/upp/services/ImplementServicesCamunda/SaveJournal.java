package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.Authority;
import com.ftn.upp.model.Journal;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.AuthorityRepository;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.services.JournalService;
import com.ftn.upp.services.UserService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
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


        HashMap<String, Object> map = mapListToDto(getJournal);


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



    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDto temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}
