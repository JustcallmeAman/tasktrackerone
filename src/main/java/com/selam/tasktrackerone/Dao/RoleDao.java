package com.selam.tasktrackerone.Dao;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import com.selam.tasktrackerone.Mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RoleDao extends JdbcDaoSupport {

    @Autowired
    public RoleDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<String> getRoleNames(Long employeeId) {
        String sqlGetEmployeeRole = "Select employee_role from employees where employee_id=?";
        String sqlGetEmployeeRoleName = "Select role_name from roles where role_id=?";
        try {
            List<Integer> roleIds= getJdbcTemplate().queryForList(sqlGetEmployeeRole,new Object[]{employeeId}, Integer.class);
            List<String> roles = new ArrayList<>();
            for (int roleId : roleIds){
                try{
                    roles.add(getJdbcTemplate().queryForObject(sqlGetEmployeeRoleName, new Object[]{roleId}, String.class));
                }
                catch (EmptyResultDataAccessException e){

                }
            }
            return roles;
        }
        catch (Exception e){
            return null;
        }
    }

    public int checkRole(int userId){
        String sqlCheckRole= "SELECT role_id FROM user_role WHERE user_id = '" + userId + "'";

        try {
            return getJdbcTemplate().queryForObject(sqlCheckRole, Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }


}
