package com.selam.tasktrackerone.Controllers;

import com.selam.tasktrackerone.Dao.EmployeeDao;
import com.selam.tasktrackerone.Dao.TaskDao;
import com.selam.tasktrackerone.Model.Employee;
import com.selam.tasktrackerone.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.Duration;
import java.util.*;

@Controller
public class TaskController {
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping(value = "viewtasks", method = RequestMethod.GET)
    public String viewTasks(Model model) {//this might be better if we pass hashmap<task, emloyee>
        try{
            List<Task> taskList= taskDao.getAllTasks();
            model.addAttribute("taskList", taskList);
        } catch (Exception e){

        }
        return "viewtasks";
    }

    @RequestMapping(value = "edittasks", method = RequestMethod.GET)
    public String editTasks (Model model){
        List<Task> taskList= taskDao.getAllTasks();
        model.addAttribute("taskList", taskList);
        return "editTasks";
    }

    @RequestMapping(value = "edittask", method = RequestMethod.POST) //for storing task info and going to task editing form
    public String edittask(Model model, @ModelAttribute(value="task") Task task) {
        model.addAttribute("task", task);
        return "editTask"; //html name
    }

    @RequestMapping(value = "submittaskedit", method = RequestMethod.POST) //for submitting the task edits.
    public String submitTaskEdit(@ModelAttribute(value = "newTask") Task task) {
        taskDao.editTask(task);
        return "redirect:edittasks";
    }

    @RequestMapping(value = "confirmTaskDeletion", method = RequestMethod.POST) //for sending to deletion Confirmation
    public String confirmTaskDeletion(Model model, @ModelAttribute(value = "task") Task task) {
        model.addAttribute("task", task);
        return "ConfirmTaskDeletion"; //html name
    }

    @RequestMapping(value = "deletetask", method = RequestMethod.POST) //for sending to deletion Confirmation
    public String deleteTask(@ModelAttribute(value = "newTask") Task task) {
        taskDao.deleteTask(task);
        return "redirect:edittasks"; //html name
    }

    @RequestMapping(value="addtask", method=RequestMethod.GET)
    public String addTask(Model model){
        Task task = new Task();
        model.addAttribute("task", task);
        return "addTask";
    }

    @RequestMapping(value="confirmnewtask", method=RequestMethod.POST)
    public String confirmNewTask(Model model, @ModelAttribute(value="task") Task task){//for submitting new task
        taskDao.addTask(task);
        return "redirect:edittasks";
    }
}
