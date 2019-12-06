package com.selam.tasktrackerone.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ManagerController {
    @RequestMapping(value = "manager", method = RequestMethod.GET)
    public String manager (){
        return "manager/index";
    }
    @RequestMapping(value = "../manager", method = RequestMethod.GET)
    public String managerFromChild (){
        return "index";
    }
}

