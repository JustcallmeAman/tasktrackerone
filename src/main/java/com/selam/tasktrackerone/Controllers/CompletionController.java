package com.selam.tasktrackerone.Controllers;

import com.selam.tasktrackerone.Dao.CompletionDao;
import com.selam.tasktrackerone.Model.Completion;
import com.selam.tasktrackerone.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        dateFormat.format(date);
        completion.setTime(date.toString());
        completionDao.InputCompletion(completion);
        return "redirect:viewtasks"; //html name
    }



}