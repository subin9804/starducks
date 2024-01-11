package org.kosta.starducks.commons;

import org.kosta.starducks.commons.notify.entity.Notify;
import org.kosta.starducks.commons.notify.repository.NotifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.List;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private NotifyRepository notifyRepository;

    @ModelAttribute
    public void notify(Model model, Principal principal) {
        if(principal != null) {
            Long empId = Long.valueOf(principal.getName());
            List<Notify> notifies = notifyRepository.findLatest4NotifyNativeQuery(empId);
            model.addAttribute("notifies", notifies);

        }


    }

}
