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
        return "employees"; //html name
    }

    @RequestMapping(value = "editemployee", method = RequestMethod.POST) //to go from employees to edit employee form
    public String editEmployee(Model model, @ModelAttribute(value="employee") Employee employee) {
        model.addAttribute("employee", employee);
        return "editEmployee"; //html name
    }

    @RequestMapping(value = "submitEmployeeEdit", method = RequestMethod.POST) //to save the edits of employee from editemployee form
    public String submitEmployeeEdit(@ModelAttribute(value = "employee") Employee employee){
        employeeDao.EditEmployee(employee.getId(), employee);
        return "redirect:employees";
    }

    @RequestMapping(value = "confirmEmployeeDeletion", method = RequestMethod.POST)
    public String deleteEmployee(@ModelAttribute(value= "employee") Employee employee){
        employeeDao.deleteEmployee(employee.getId());
        return "redirect:employees";
    }

    @RequestMapping(value = "addemployee", method = RequestMethod.GET)
    public String addEmployee(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "addEmployee";
    }

    @RequestMapping(value = "addnewemployee", method = RequestMethod.POST)
    public String addNewEmployee(@ModelAttribute(value= "employee") Employee employee){
        employeeDao.addEmployee(employee);
        return "redirect:employees";
    }

}


