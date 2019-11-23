package com.selam.tasktrackerone.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class ManagerController {
    @RequestMapping(value = "manager", method = RequestMethod.GET)
    public String manager (Model model){
        //model.addAttribute("date", "");
        return "manager";
    }
}

