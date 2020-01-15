package com.ftn.upp.handlers;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.ScientificField;
import com.ftn.upp.repository.ScienceFieldRepository;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveScientificHandler implements TaskListener {


    @Autowired
    private ScienceFieldRepository scienceFieldRepository;

    @Override
    public void notify(DelegateTask delegateTask) {


//        List<FormSubmissionDto> scientificField = (List<FormSubmissionDto>) execution.getVariable("scientificField");
//
////        HashMap<String, Object> map = mapListToDto(scientificField);
//
//        FormSubmissionDto scienceDTO = null;
//        for (FormSubmissionDto dto : scientificField) {
//            if (dto.getFieldId().equals("naucnaOblast")) {
//                scienceDTO = dto;
//            }
//        }
//
//        ScientificField scientificFieldExists = this.scienceFieldRepository.findScientificFieldByName(scienceDTO.getFieldValue());
//
//        if(scientificFieldExists == null) {
//            ScientificField scientificFieldNew = new ScientificField();
//            scientificFieldNew.setName(scienceDTO.getFieldValue());
//            this.scienceFieldRepository.save(scientificFieldNew);
//        }

    }
}
