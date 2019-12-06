package com.selam.tasktrackerone.Dao;

import com.selam.tasktrackerone.Mapper.EmployeeMapper;
import com.selam.tasktrackerone.Model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class EmployeeDao extends JdbcDaoSupport {
    @Autowired
    public EmployeeDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Autowired
    static JdbcTemplate jdbcTemplate;

    public List<Employee> getAllEmployees(){
        String sqlGetAllEmployeeId = "SELECT id FROM employees";
        String sqlGetAllEmployeesById = "SELECT * FROM employees WHERE id = ?";
        try {
            List<Long> employeeIds= getJdbcTemplate().queryForList(sqlGetAllEmployeeId,new Object[]{}, Long.class);
            List<Employee> employees = new ArrayList<>();
            EmployeeMapper employeeMapper = new EmployeeMapper();
            for (Long employeeId : employeeIds){
                try{
                    employees.add(getJdbcTemplate().queryForObject(sqlGetAllEmployeesById, new Object[]{employeeId}, employeeMapper));
                }
                catch (EmptyResultDataAccessException e){

                }
            }
            return employees;
        }
        catch (Exception e){
            return null;
        }
    }

    public void EditEmployee(Long id, Employee updatedEmployee, String enabled){

        try{
            String sqlEditEmployee= "UPDATE employees SET employee_password=?, enabled=? WHERE id=?";
            int enable;
            if(enabled.equals("active")){
                enable =1;
            } else {
                enable =0;
            }
            Object[] vals = new Object[]{ updatedEmployee.getEncryptedPassword(), enable, id};
            getJdbcTemplate().update(sqlEditEmployee, vals);


            /*String sqlGiveNewAuthority = " UPDATE authorities SET authority=? WHERE employee_username=?";
            String authority;
            if (updatedEmployee.getRole()==1){
                authority = "manager";
            } else {
                authority= "user";
            }
            vals = new Object[]{authority, updatedEmployee.getUsername()};
            getJdbcTemplate().update(sqlGiveNewAuthority,vals);*/
        }
        catch (Exception e){
        }

    }

    public Employee FindEmployeeAccount (String userName) {
        String sql = EmployeeMapper.BASE_SQL + " where e.username = ? ";

        Object[] params = new Object[] { userName };
        EmployeeMapper mapper = new EmployeeMapper();
        try {
            Employee employee = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return employee;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void deleteEmployee(Long id) {
        String sqlDeleteEmployee= "DELETE FROM employees WHERE id=?";
        getJdbcTemplate().update(sqlDeleteEmployee, id);
    }

    public void addEmployee (Employee employee){
        String sqlAddEmployee = "INSERT INTO employees (employee_username, employee_password, employee_role) VALUES (?, ?, ?)";
        getJdbcTemplate().update(sqlAddEmployee, employee.getUsername(), employee.getEncryptedPassword(), employee.getRole());
        String sqlAddAuthority = "INSERT INTO authorities (authority, employee_username) VALUES (?, ?)";
        if(employee.getRole()==1){//employee is a manager
            getJdbcTemplate().update(sqlAddAuthority, "manager", employee.getUsername());
        } else if (employee.getRole()==2){//employee is a user
            getJdbcTemplate().update(sqlAddAuthority, "user", employee.getUsername());
        }
    }

    public Employee getEmployeeById(Long employeeId){
        String sqlgetEmployee = "SELECT * FROM employees WHERE id=?";
        return getJdbcTemplate().queryForObject(sqlgetEmployee,new Object[]{employeeId}, new EmployeeMapper());
    }

    public Employee getEmployeeByUsername(String username){
        String sqlgetEmployee = "SELECT * FROM employees WHERE employee_username=?";
        return getJdbcTemplate().queryForObject(sqlgetEmployee,new Object[]{username}, new EmployeeMapper());
    }

    public List<String> getUsernames(){
        String sqlGetUsernames = "SELECT employee_username FROM employees";
        return getJdbcTemplate().queryForList(sqlGetUsernames,new Object[]{}, String.class);
    }

    public List<String> getEnabledUsernames(){
        String sqlGetUsernames = "SELECT employee_username FROM employees WHERE enabled=1";
        return getJdbcTemplate().queryForList(sqlGetUsernames,new Object[]{}, String.class);
    }
}
