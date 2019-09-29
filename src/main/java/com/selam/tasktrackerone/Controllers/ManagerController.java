package com.selam.tasktrackerone.Controllers;

import com.selam.tasktrackerone.Dao.TaskDao;
import com.selam.tasktrackerone.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.Duration;
import java.util.List;

@Controller
public class ManagerController {
        @Autowired
        private TaskDao taskDao;

        @RequestMapping(value = "manager", method = RequestMethod.GET)
            public String manager (Model model){
            return "manager";
        }
        @RequestMapping(value = "edittasks", method = RequestMethod.GET)
        public String edittasks (Model model){
            List<Task> taskList= taskDao.getAllTasks();
            model.addAttribute("taskList", taskList);
            return "edittasks";
        }
        @RequestMapping(value = "deletetasks", method = RequestMethod.GET)
        public String editemployees (Model model){

            return "editemployees";
        }

    }

