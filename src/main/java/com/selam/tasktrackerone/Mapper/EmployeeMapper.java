package com.selam.tasktrackerone.Mapper;

import com.selam.tasktrackerone.Model.Completion;
import com.selam.tasktrackerone.Model.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<Employee> {

    public static final String BASE_SQL = "Select e.Id, e.employee_username, e.employee_password, e.employee_role From employees e";

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("Id");
        String username = rs.getString("employee_username");
        String password = rs.getString("employee_password");
        int role = rs.getInt("employee_role");
        return new Employee(id, username, password, role);
    }

}
