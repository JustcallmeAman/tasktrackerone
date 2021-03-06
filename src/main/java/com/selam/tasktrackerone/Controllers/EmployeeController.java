package com.selam.tasktrackerone.Controllers;

import com.selam.tasktrackerone.Dao.EmployeeDao;
import com.selam.tasktrackerone.Model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
public class EmployeeController {
    @Autowired
    private EmployeeDao employeeDao;
    @RequestMapping(value = "manager/employees", method = RequestMethod.GET)
    public String viewEmployees(Model model) {
        List<Employee> employeeList= employeeDao.getAllEmployees();
        model.addAttribute("employeeList", employeeList);
        return "manager/employees"; //html name
    }

    @RequestMapping(value = "manager/editemployee", method = RequestMethod.POST) //to go from employees to edit employee form
    public String editEmployee(Model model, @ModelAttribute(value="employee") Employee employee) {
        model.addAttribute("employee", employee);
        return "manager/editEmployee";

    }

    @RequestMapping(value = "manager/submitEmployeeEdit", method = RequestMethod.POST) //to go from employees to edit employee form
    public String submitEmployeeEdit(@ModelAttribute(value = "employee") Employee employee, @RequestParam("pwd1") String pwd1, @RequestParam("pwd2") String pwd2, @RequestParam("enable") String enabled,Model model){
        if (pwd1.equals(pwd2)){
            if (pwd1!=null && !pwd1.equals("")){
                employee.setEncryptedPassword(pwd1);
            }
            employeeDao.EditEmployee(employee.getId(), employee, enabled);
            return "redirect:employees";
        } else {
            model.addAttribute("employee", employee);
            return "manager/editemployeeerror";
        }
    }

    @RequestMapping(value = "manager/confirmEmployeeDeletion", method = RequestMethod.POST)
    public String deleteEmployee(@ModelAttribute(value= "employee") Employee employee){
        employeeDao.deleteEmployee(employee.getId());
        return "redirect:employees";
    }

    @RequestMapping(value = "manager/addemployee", method = RequestMethod.GET)
    public String addEmployee(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "manager/addEmployee";
    }

    @RequestMapping(value = "manager/addnewemployee", method = RequestMethod.POST)
    public String addNewEmployee(@ModelAttribute(value= "employee") Employee employee){
        employeeDao.addEmployee(employee);
        return "redirect:employees";
    }

}


