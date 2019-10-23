package com.selam.tasktrackerone.Controllers;

import com.selam.tasktrackerone.Dao.TaskDao;
import com.selam.tasktrackerone.Model.Completion;
import com.selam.tasktrackerone.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            return "editTasks";
        }
        @RequestMapping(value = "deletetasks", method = RequestMethod.GET)
        public String editemployees (Model model){
            return "editemployees";
        }

    @RequestMapping(value = "edittask", method = RequestMethod.POST) //for storing task info and going to task editing form
    public String edittask(Model model, @ModelAttribute(value="task") Task task) {
        model.addAttribute("task", task);
        return "editTask"; //html name
    }

    @RequestMapping(value = "submittaskedit", method = RequestMethod.POST) //for submitting the task edits.
    public String submitTaskEdit(@ModelAttribute(value = "newTask") Task task) {
        taskDao.editTask(task);
        return "editTasks"; //html name
    }
    @RequestMapping(value = "confirmTaskDeletion", method = RequestMethod.POST) //for sending to deletion Confirmation
    public String confirmDelete(Model model, @ModelAttribute(value = "task") Task task) {
        model.addAttribute("task", task);
        return "ConfirmTaskDeletion"; //html name
    }

    @RequestMapping(value = "deletetask", method = RequestMethod.POST) //for sending to deletion Confirmation
    public String deleteTask(Model model, @ModelAttribute(value = "newTask") Task task) {
        taskDao.deleteTask(task);
        return "editTasks"; //html name
    }

    }

