package com.ftn.upp.services.ImplementServicesCamunda;

import com.ftn.upp.model.User;
import com.ftn.upp.repository.UserRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class GetLoggedUser implements JavaDelegate {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        User user =new User();

        user = getCurrentUser();

        if (user == null) {
            System.out.println("Niko nije ulogovan!");
            delegateExecution.setVariable("ulogovan", "false", delegateExecution.getCurrentActivityId());
        }
        else
        {
            System.out.println("Ulogovan je [provera ulogovan]: " + user.getUsername());
            delegateExecution.setVariable("ulogovan", "true", delegateExecution.getCurrentActivityId());
        }
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = "";
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
        } else {
             username = principal.toString();
        }
        User k = userRepository.findUserByUsername(username);
        if (k != null)
        {
            String kIme = k.getUsername();
            System.out.println("Username getCurrentUser je: " + kIme);
        }
        else
        {
            System.out.println("Korisnik je null!");
        }


        return k;
    }


}
