package com.selam.tasktrackerone.Controllers;

import com.selam.tasktrackerone.Dao.EmployeeDao;
import com.selam.tasktrackerone.Model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
@Controller
public class EmployeeController {
    @Autowired
    private EmployeeDao employeeDao;
    @RequestMapping(value = "employees", method = RequestMethod.GET)
    public String viewEmployees(Model model) {
        List<Employee> employeeList= employeeDao.getAllEmployees();
        model.addAttribute("employeeList", employeeList);
        return "editemployees"; //html name
    }

    @RequestMapping(value = "editEmployee", method = RequestMethod.POST) //to go from employees to edit employee form
    public String editEmployee (@ModelAttribute(value="employee") Employee employee){
        return "editEmployee";
    }

    @RequestMapping(value = "submitEmployeeEdit", method = RequestMethod.POST) //to save the edits of employee from editemployee form
    public String submitEmployeeEdit(@ModelAttribute(value = "employee") Employee employee){
        employeeDao.EditEmployee(employee.getId(), employee);
        return "editemployees";
    }

    @RequestMapping(value = "deleteEmployee", method = RequestMethod.POST)
    public String deleteEmployee(){
        return "editemployees";
    }

}


