package com.selam.tasktrackerone.Controllers;

import com.selam.tasktrackerone.Dao.TaskDao;
import com.selam.tasktrackerone.Model.FrequentTask;
import com.selam.tasktrackerone.Model.PeriodicalTask;
import com.selam.tasktrackerone.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ManagerController {
    @RequestMapping(value = "manager", method = RequestMethod.GET)
    public String manager (Model model){
        return "manager";
    }

    }

