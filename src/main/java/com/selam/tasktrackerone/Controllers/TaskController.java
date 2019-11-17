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

import java.time.Duration;
import java.util.*;

@Controller
public class TaskController {
    @Autowired
    private TaskDao taskDao;

    @RequestMapping(value = "viewtasks", method = RequestMethod.GET)
    public String viewtasks(Model model) {
        List<Task> taskList= taskDao.getAllTasks();
        for(Task t : taskList){
            t.setLastDone(taskDao.getLastDoneTime(t));
            if(taskDao.getTaskType(t)==1){//periodical
                t.setNextDeadline(taskDao.getNextPeriodicalDeadline(t));
            }
            else if(taskDao.getTaskType(t)==2){//frequent
                Duration frequency= taskDao.getFrequency(t.getId());
                System.out.print("FREQUENCY: "+frequency);
                t.setNextDeadline(t.getLastDone().plus(frequency));
            }
        }
        model.addAttribute("taskList", taskList);
        return "viewtasks";
    }

    @RequestMapping(value = "edittasks", method = RequestMethod.GET)
    public String edittasks (Model model){
        List<Task> taskList= taskDao.getAllTasks();
        model.addAttribute("taskList", taskList);
        return "editTasks";
    }
    @RequestMapping(value = "deletetasks", method = RequestMethod.GET)
    public String editemployees (Model model){
        return "employees";
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
    public String confirmDelete(Model model, @ModelAttribute(value = "task") Task task) {
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

    @RequestMapping(value="addnewtask", method=RequestMethod.POST)
    public String addNewTask(Model model, @ModelAttribute(value="task") Task task){
        if (task.getType()==1){//periodical
            PeriodicalTask periodicalTask = new PeriodicalTask(task.getId(), task.getName(), task.getDescription(), null, null, null);
            model.addAttribute("task", periodicalTask);
            return "addDeadlines";
        } else {//frequent
            FrequentTask fTask= new FrequentTask(task.getId(), task.getName(), task.getDescription(), null, null, null);
            model.addAttribute("task",fTask);
            return "addFrequency";
        }
    }

    @RequestMapping(value="addtaskdeadlines", method=RequestMethod.POST)
    public String addPeriodicalTask(@ModelAttribute(value="periodicalTask")PeriodicalTask periodicalTask){
        taskDao.addTask(periodicalTask); //make sure this task has values enterd in "addnewtask" and not another brand new blank task
        taskDao.addDeadlines(periodicalTask);
        return"viewtasks";
    }

    @RequestMapping(value="addtaskfrequency", method=RequestMethod.POST)
    public String addFrequentTask(@ModelAttribute(value="frequentTask")FrequentTask frequentTask){
        taskDao.addTask(frequentTask);
        taskDao.addFrequency(frequentTask);
        return "viewtasks";
    }
}
