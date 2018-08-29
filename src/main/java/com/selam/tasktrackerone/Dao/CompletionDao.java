package com.selam.tasktrackerone.Dao;

import com.selam.tasktrackerone.Mapper.CompletionMapper;
import com.selam.tasktrackerone.Model.Completion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
@Transactional
public class CompletionDao extends JdbcDaoSupport {
    @Autowired
    public CompletionDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Autowired
    static
    JdbcTemplate jdbcTemplate;

    public void InputCompletion(Completion c){
        int id=c.getId();
        String time=c.getTime();
        String comment=c.getComment();
        int employeeId=c.getEmployeeId();
        int taskId=c.getTaskId();
        String sqlInputCompletion ="INSERT INTO completion(completion_id, completion_time, completion_comment, employee_id, task_id) VALUE(?,?,?,?,?)";
        getJdbcTemplate().update(sqlInputCompletion, id, time, comment, employeeId, taskId);
    }

}
