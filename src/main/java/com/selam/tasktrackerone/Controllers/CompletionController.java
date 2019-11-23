package com.selam.tasktrackerone.Controllers;

import com.selam.tasktrackerone.Dao.CompletionDao;
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

    @RequestMapping(value = "addcompletion", method = RequestMethod.POST) //for storing task info and going to completion form from viewtasks
    public String addCompletion(Model model, @ModelAttribute(value="task") Task task) {
        Completion completion=new Completion();
        model.addAttribute("completion", completion);
        return "completionform"; //html name
    }

    @RequestMapping(value = "submitcompletion", method = RequestMethod.POST) //for submitting the completion form after its filled out.
    public String submitCompletion(@ModelAttribute(value = "completion") Completion completion) {
        LocalDateTime time= LocalDateTime.now();
        completion.setTime(time);
        completionDao.inputCompletion(completion);
        return "redirect:viewtasks"; //html name
    }


    @RequestMapping(value = "completions", method = RequestMethod.POST) //for submitting the completion form after its filled out.
    public String showCompletions(Model model, @RequestParam("date") String dateString) {
        LocalDate date= LocalDate.parse(dateString);
        LocalDateTime dateTime= LocalDateTime.of(date, LocalTime.of(00,00));
        List<Wrapper> wrappers= completionDao.getWrappers(dateTime);
        model.addAttribute("wrappers", wrappers);
        return "completions"; //html name
    }




}