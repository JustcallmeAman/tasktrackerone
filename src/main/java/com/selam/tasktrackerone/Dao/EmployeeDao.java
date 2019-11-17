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

    public void EditEmployee(Long id, Employee updatedEmployee){
        String sqlEditEmployee= "UPDATE employees SET employee_username=?, employee_role=? WHERE id=?";
        try{
            getJdbcTemplate().update(sqlEditEmployee, updatedEmployee.getUsername(), updatedEmployee.getRole(), id);
        }
        catch (Exception e){
            System.out.print("id idnt work");
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
}
