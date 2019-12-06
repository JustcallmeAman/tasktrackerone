package com.selam.tasktrackerone.Controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.stereotype.Controller
public class Controller {
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "../viewtasks", method = RequestMethod.GET)
    public String viewTasks (){
        return "../viewtasks";
    }

}
