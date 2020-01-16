package com.ftn.upp.controller;

import java.util.HashMap;
import java.util.List;

import com.ftn.upp.dto.FormFieldsDto;
import com.ftn.upp.dto.FormSubmissionDto;
import com.ftn.upp.model.ScientificField;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.ScienceFieldRepository;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.security.validation.RegularExpressions;
import com.ftn.upp.services.UserService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("api/welcome")
public class DummyController {
	@Autowired
	IdentityService identityService;       // bavi se userima i grupama

	@Autowired
	private RuntimeService runtimeService;  // bavi pokrenutim procesima

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	TaskService taskService;

	@Autowired
	FormService formService;


	@Autowired
	private ScienceFieldRepository scienceFieldRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	//kliknuto na registraciju ovo gadja, treba da se pokrene proces, pokupi polja forme i vrati na front
	@GetMapping(path = "/get", produces = "application/json")
    public @ResponseBody
	FormFieldsDto get() {

		ProcessInstance pi = runtimeService.startProcessInstanceByKey("registracijaKorisnika");

		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}

        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }

	@GetMapping(path = "/get/next/{procesInstanceId}", produces = "application/json")
	public @ResponseBody
	FormFieldsDto getNext(@PathVariable String procesInstanceId) {


		Task task = taskService.createTaskQuery().processInstanceId(procesInstanceId).list().get(0);

		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}

		return new FormFieldsDto(task.getId(), procesInstanceId, properties);
	}


	@GetMapping(path = "/get/admin", produces = "application/json")
	public @ResponseBody
	FormFieldsDto getAdmin() {


    	Task task = taskService.createTaskQuery().taskCandidateGroup("demo").list().get(0);
//		Task task = taskService.createTaskQuery().processInstanceId(procesInstanceId).taskCandidateGroup("demo").list().get(0);
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}


		return new FormFieldsDto(task.getId(), task.getProcessInstanceId(), properties);
	}


	@PostMapping(path = "/post/register/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postRegister(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {

        RegularExpressions regularExpressions = new RegularExpressions();


        boolean isImeValid = true;
        boolean isPrezimeValid = true ;
        boolean isGradValid = true;
        boolean isDrzavaValid = true;
        boolean isTitulaValid = true;
        boolean isEmailValid = true;
        boolean isKorisnickoImeValid = true;
        boolean isBrNaucnihValid = true;
        boolean userExistsUsername = false;
        boolean userExistsEmail = false;
        String imeKorisnika = "";
        String emailKorisnika = "";



	    for(FormSubmissionDto dto1 : dto ){
	         if(dto1.getFieldId().equals("ime") ) {
	              if(dto1.getFieldValue().isEmpty()){
	                  isImeValid =false;
                  } else {
                      isImeValid = regularExpressions.isNameValid(dto1.getFieldValue());
                  }
             }else if (dto1.getFieldId().equals("prezime") ) {
                 if(dto1.getFieldValue().isEmpty()){
                     isPrezimeValid =false;
                 } else {
                     isPrezimeValid = regularExpressions.isNameValid(dto1.getFieldValue());
                 }
             } else if (dto1.getFieldId().equals( "grad")) {
                 if(dto1.getFieldValue().isEmpty()){
                     isGradValid =false;
                 } else {
                     isGradValid = regularExpressions.isNameValid(dto1.getFieldValue());
                 }
             }else if (dto1.getFieldId().equals("drzava")) {
                 if(dto1.getFieldValue().isEmpty()){
                     isDrzavaValid =false;
                 } else {
                     isDrzavaValid = regularExpressions.isNameValid(dto1.getFieldValue());
                 }
            }else if (dto1.getFieldId().equals("titula")) {
	             if(!dto1.getFieldValue().isEmpty()){
	                isTitulaValid = regularExpressions.isNameValid(dto1.getFieldValue());
                 }
            }else if (dto1.getFieldId().equals("email")) {
                 emailKorisnika = dto1.getFieldValue();
                 if(dto1.getFieldValue().isEmpty()){
                     isEmailValid =false;
                 } else {
                     isEmailValid = regularExpressions.isEmailValid(dto1.getFieldValue());
                 }
             }else if (dto1.getFieldId().equals("korisnickoIme")) {
                 imeKorisnika = dto1.getFieldValue();
                 if(dto1.getFieldValue().isEmpty()){
                     isKorisnickoImeValid =false;
                 } else {
                     isKorisnickoImeValid = regularExpressions.isUsernameValid(dto1.getFieldValue());
                 }
             }
//             else if (dto1.getFieldId().equals("sifra")) { }
             else if (dto1.getFieldId().equals("naucneOblasti")) {
                 if(dto1.getFieldValue().isEmpty()){
                     isBrNaucnihValid =false;
                 } else {
                     isBrNaucnihValid = regularExpressions.isNumberValid(dto1.getFieldValue());
                 }
            }
        }


        User userSaUsername = userService.findUserByUsername(imeKorisnika);
	    User saEmailom = userService.findUserByEmail(emailKorisnika);
        if(saEmailom != null){
            userExistsEmail =true;
        }
	    if(userSaUsername != null ){
            userExistsUsername = true;
        }

        if(!isImeValid || !isPrezimeValid || !isGradValid || !isDrzavaValid || userExistsUsername || userExistsEmail ||
                !isTitulaValid|| !isEmailValid || !isKorisnickoImeValid || !isBrNaucnihValid ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }


		HashMap<String, Object> map = this.mapListToDto(dto);

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "registration", dto);
		runtimeService.setVariable(processInstanceId, "procesInstanceId", processInstanceId);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }


	@PostMapping(path = "/post/scientificField/{taskId}", produces = "application/json")
	public @ResponseBody ResponseEntity postForScientificField(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {

        RegularExpressions regularExpressions = new RegularExpressions();
        boolean isNaucnaValid = true;

        for(FormSubmissionDto dto1 : dto ) {
            if (dto1.getFieldId().equals("naucnaOblast")) {
                if(dto1.getFieldValue().isEmpty()){
                    isNaucnaValid =false;
                } else {
                    isNaucnaValid = regularExpressions.isNameValid(dto1.getFieldValue());
                }
            }
        }

        if(!isNaucnaValid){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        HashMap<String, Object> map = this.mapListToDto(dto);


		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		List<FormSubmissionDto> registrationDTO = (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId,"registration");

		FormSubmissionDto usernameDTO = null;
		for (FormSubmissionDto dto1 : registrationDTO) {
			if (dto1.getFieldId().equals("korisnickoIme")) {
				usernameDTO = dto1;
			}
		}

		User user = this.userService.findUserByUsername(usernameDTO.getFieldValue());
		FormSubmissionDto scienceDTO = null;
		for (FormSubmissionDto dto1 : dto) {
			if (dto1.getFieldId().equals("naucnaOblast")) {
				scienceDTO = dto1;
			}
		}

		ScientificField scientificFieldExists = this.scienceFieldRepository.findScientificFieldByName(scienceDTO.getFieldValue());

		if(scientificFieldExists == null) {
			ScientificField scientificFieldNew = new ScientificField();
			scientificFieldNew.setName(scienceDTO.getFieldValue());
			this.scienceFieldRepository.save(scientificFieldNew);
			user.getScientificFields().add(scientificFieldNew);
			this.userRepository.save(user);
		}

		formService.submitTaskForm(taskId, map);
		return new ResponseEntity<>(HttpStatus.OK);
	}


    @PostMapping(path = "/post/activateUser/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postActivateUser(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);

        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

	@PostMapping(path = "/post/acceptReviewer/{taskId}", produces = "application/json")
	public @ResponseBody ResponseEntity postAcceptReviewer(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		formService.submitTaskForm(taskId, map);
		return new ResponseEntity<>(HttpStatus.OK);
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
