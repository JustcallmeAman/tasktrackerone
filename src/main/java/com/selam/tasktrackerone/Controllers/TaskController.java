package com.selam.tasktrackerone.Controllers;

import com.selam.tasktrackerone.Dao.TaskDao;
import com.selam.tasktrackerone.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

}
