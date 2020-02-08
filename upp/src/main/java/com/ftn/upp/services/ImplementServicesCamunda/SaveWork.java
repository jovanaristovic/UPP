package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.ScientificField;
import com.ftn.upp.model.User;
import com.ftn.upp.model.Work;
import com.ftn.upp.repository.AuthorityRepository;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.repository.WorkRepository;
import com.ftn.upp.services.ScientificFieldService;
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

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SaveWork implements JavaDelegate {
    @Autowired
    private UserService userService;

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    private ScientificFieldService scientificFieldService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkRepository workRepository;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormSubmissionDto> workDto= (List<FormSubmissionDto>) delegateExecution.getVariable("newWork");
        String pfdName = (String) delegateExecution.getVariable("pdfName");
        FormSubmissionDto usernameDto = (FormSubmissionDto) delegateExecution.getVariable("username");

        FormSubmissionDto scientificField = null;
        for (FormSubmissionDto dto : workDto) {
            if (dto.getFieldId().equals("name")) {
                scientificField = dto;
            }

        }
        ScientificField scientificFieldBase = this.scientificFieldService.findByName(scientificField.getFieldValue());
        Work work = new Work(workDto);
        work.setScientificField(scientificFieldBase);
        work.setPdf(pfdName);

        User user = this.userService.findUserByUsername(usernameDto.getFieldValue());
        if(work.getUsers() != null){
            work.getUsers().add(user);

        } else {
            List <User> users = new ArrayList<>();
            users.add(user);
            work.setUsers(users);
        }
        this.workRepository.save(work);


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
