package com.selam.tasktrackerone.Controllers;

import com.selam.tasktrackerone.Dao.CompletionDao;
import com.selam.tasktrackerone.Dao.EmployeeDao;
import com.selam.tasktrackerone.Model.Completion;
import com.selam.tasktrackerone.Model.Task;
import com.selam.tasktrackerone.Model.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CompletionController {
    @Autowired
    private CompletionDao completionDao;
    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping(value = "addcompletion", method = RequestMethod.POST) //for storing task info and going to completion form from viewtasks
    public String addCompletion(Model model, @ModelAttribute(value="task") Task task) {
        Completion completion=new Completion();
        List<String> usernames = employeeDao.getEnabledUsernames();
        model.addAttribute("completion", completion);
        model.addAttribute("usernames", usernames);
        return "completionform"; //html name
    }

    @RequestMapping(value = "submitcompletion", method = RequestMethod.POST) //for submitting the completion form after its filled out.
    public String submitCompletion(@ModelAttribute(value = "completion") Completion completion) {
        LocalDateTime time= LocalDateTime.now();
        completion.setTime(time);
        completionDao.inputCompletion(completion);
        return "redirect:viewtasks"; //html name
    }

    @RequestMapping(value = "manager/completions", method = RequestMethod.POST) //for submitting the completion form after its filled out.
    public String showCompletions(Model model, @RequestParam("startDate") String startDateString, @RequestParam("endDate") String endDateString) {
        try{
            LocalDate startDate= LocalDate.parse(startDateString);
            LocalDateTime startDateTime= LocalDateTime.of(startDate, LocalTime.of(00,00));
            LocalDate endDate= LocalDate.parse(endDateString);
            LocalDateTime endDateTime= LocalDateTime.of(endDate, LocalTime.of(00,00));

            List<Wrapper> wrappers= completionDao.getWrappers(startDateTime, endDateTime);
            model.addAttribute("wrappers", wrappers);
            return "manager/completions"; //html name
        } catch (Exception e){

        }
        return "manager/completions";
    }




}