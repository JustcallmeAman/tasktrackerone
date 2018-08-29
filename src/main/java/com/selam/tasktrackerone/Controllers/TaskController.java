package com.selam.tasktrackerone.Controllers;

import com.selam.tasktrackerone.Dao.TaskDao;
import com.selam.tasktrackerone.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {
    @Autowired
    private TaskDao taskDao;

    //@Autowired
    //private Task task;

    @RequestMapping(value = "viewtasks", method = RequestMethod.GET)
    public String viewtasks(Model model) {
        List<Task> taskList= taskDao.getAllTasks();
        model.addAttribute("taskList", taskList);
        return "viewtasks"; //html name
    }


}
