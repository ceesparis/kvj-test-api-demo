package com.example.kvkpliegerdemo3.controller;

import com.example.kvkpliegerdemo3.form.RegistrationForm;
import com.example.kvkpliegerdemo3.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;


@Controller("RegistrationController")
public class RegistrationController {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    @GetMapping(value="/register")
    protected String registryPage(@ModelAttribute("RegistrationForm") RegistrationForm form, HttpServletRequest request) {
        return "registrypage";
    }

    @PostMapping(value="/register")
    protected RedirectView register(@ModelAttribute("RegistrationForm") RegistrationForm form, HttpServletRequest request,
                                    RedirectAttributes redirectAttributes){

        String email = form.getEmail();
        String password = form.getPassword();

        // make data subsist across page refreshing
        request.getSession().setAttribute("form", form);

        boolean passwordIsThere = !(password == null || password.isEmpty());
        boolean emailIsThere = !(email == null || email.isEmpty());

        if (!emailIsThere) {
            redirectAttributes.addFlashAttribute("errorMessageEmail", "required");
        }

        if (!passwordIsThere) {
            redirectAttributes.addFlashAttribute("errorMessagePassword", "required");
        }

        if (!(passwordIsThere && emailIsThere)){
            return new RedirectView("register", true);
        }

        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        account.setCompanyName(form.getCompanyName());
        account.setKvkNumber(form.getKvkNumber());
        account.setLocation(form.getLocation());

        request.getSession().setAttribute("form", null);
        request.getSession().setAttribute("account", account);
        return new RedirectView("landingpage", true);
    }

    @GetMapping("landingpage")
    protected String landingPage(){
        return "landingpage";
    }
}
