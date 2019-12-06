package com.selam.tasktrackerone.Controllers;

import com.selam.tasktrackerone.Dao.TaskDao;
import com.selam.tasktrackerone.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
public class TaskController {
    @Autowired
    private TaskDao taskDao;

    @RequestMapping(value = {"viewtasks", "/", "index"}, method = RequestMethod.GET)
    public String viewTasks(Model model) {//this might be better if we pass hashmap<task, emloyee>
        try{
            List<Task> taskList= taskDao.getAllEnabledTasks();
            model.addAttribute("taskList", taskList);
        } catch (Exception e){

        }
        return "viewtasks";
    }

    @RequestMapping(value = "manager/edittasks", method = RequestMethod.GET)
    public String editTasks (Model model){
        List<Task> taskList= taskDao.getAllTasks();
        model.addAttribute("taskList", taskList);
        return "manager/editTasks";
    }

    @RequestMapping(value = "manager/edittask", method = RequestMethod.POST) //for storing task info and going to task editing form
    public String edittask(Model model, @ModelAttribute(value="task") Task task) {
        model.addAttribute("task", task);
        return "manager/editTask"; //html name
    }

    @RequestMapping(value = "manager/submittaskedit", method = RequestMethod.POST) //for submitting the task edits.
    public String submitTaskEdit(@ModelAttribute(value = "newTask") Task task) {
        taskDao.editTask(task);
        return "redirect:edittasks";
    }

    @RequestMapping(value = "manager/confirmTaskEnableDisable", method = RequestMethod.POST) //for sending to deletion Confirmation
    public String confirmTaskEnableDisable(Model model, @ModelAttribute(value = "task") Task task) {
        model.addAttribute("task", task);
        int status = taskDao.getTaskStatus(task);
        if (status ==1){
            return "manager/ConfirmTaskDisable"; //html name
        } else {
            return "manager/ConfirmTaskEnable";
        }
    }

    @RequestMapping(value = "manager/disabletask", method = RequestMethod.POST)
    public String disableTask(@ModelAttribute(value = "newTask") Task task) {
        taskDao.disableTask(task);
        return "redirect:edittasks"; //html name
    }

    @RequestMapping(value = "manager/enabletask", method = RequestMethod.POST)
    public String enableTask(@ModelAttribute(value = "newTask") Task task) {
        taskDao.enableTask(task);
        return "redirect:edittasks"; //html name
    }

    @RequestMapping(value="manager/addtask", method=RequestMethod.GET)
    public String addTask(Model model){
        Task task = new Task();
        model.addAttribute("task", task);
        return "manager/addTask";
    }

    @RequestMapping(value="manager/confirmnewtask", method=RequestMethod.POST)
    public String confirmNewTask(Model model, @ModelAttribute(value="task") Task task){//for submitting new task
        taskDao.addTask(task);
        return "redirect:edittasks";
    }

}
