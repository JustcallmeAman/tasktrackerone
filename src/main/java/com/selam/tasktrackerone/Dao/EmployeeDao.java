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
        String sqlGetAllEmployeeId = "SELECT employee_id FROM employees";
        String sqlgetAllEmployeesbyId = "SELECT * FROM employees WHERE employee_id = ?";
        try {
            List<Long> employeeIds= getJdbcTemplate().queryForList(sqlGetAllEmployeeId,new Object[]{}, Long.class);
            List<Employee> employees = new ArrayList<>();
            EmployeeMapper employeeMapper = new EmployeeMapper();
            for (Long employeeId : employeeIds){
                try{
                    employees.add(getJdbcTemplate().queryForObject(sqlgetAllEmployeesbyId, new Object[]{employeeId}, employeeMapper));
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

    public void editEmployee(Long id, Employee updatedEmployee){
        String sqlEditEmployee= "UPDATE employees SET employee_username=?, employee_role=? WHERE employee_id=?";
        try{
            getJdbcTemplate().update(sqlEditEmployee, updatedEmployee.getUsername(), updatedEmployee.getRole(), id);
        }
        catch (Exception e){
        }

    }

    public Employee findEmployeeAccount (String userName) {
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

}
