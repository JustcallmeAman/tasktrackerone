package com.selam.tasktrackerone.Controllers;

import com.selam.tasktrackerone.Dao.CompletionDao;
import com.selam.tasktrackerone.Dao.EmployeeDao;
import com.selam.tasktrackerone.Dao.TaskDao;
import com.selam.tasktrackerone.Model.Employee;
import com.selam.tasktrackerone.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Controller
public class EmployeeController {
    @Autowired
    private EmployeeDao employeeDao;
    @RequestMapping(value = "Manager/employees", method = RequestMethod.GET)
    public String viewEmployees(Model model) {
        List<Employee> employeeList= employeeDao.getAllEmployees();
        model.addAttribute("employeeList", employeeList);
        return "Manager/employees"; //html name
    }

    @RequestMapping(value = "editEmployee", method = RequestMethod.POST) //to go from employees to edit employee form
    public String editEmployee (@ModelAttribute(value="employee") Employee employee){
        return "Manager/editEmployee";
    }
    @RequestMapping(value = "submitEmployeeEdit", method = RequestMethod.POST) //to save the edits of employee from editemployee form
    public String submitEmployeeEdit(@ModelAttribute(value = "employee") Employee employee){
        employeeDao.editEmployee(employee.getId(), employee);
        return "Manager/employees";
    }
    @RequestMapping(value = "deleteEmployee", method = RequestMethod.POST)
    public String deleteEmployee(){
        return "manager/employees";
    }

}
