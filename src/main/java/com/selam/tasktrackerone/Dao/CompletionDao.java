package com.selam.tasktrackerone.Dao;

import com.selam.tasktrackerone.Model.Completion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Repository
@Transactional
public class CompletionDao extends JdbcDaoSupport {
    @Autowired
    public CompletionDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Autowired
    private TaskDao taskDao;

    @Autowired
    static
    JdbcTemplate jdbcTemplate;

    public void inputCompletion(Completion c){
        //stores completion in database, and if completed task is periodical, links completion to relevant deadline.
        LocalDateTime time=c.getTime();
        String comment=c.getComment();
        Long employeeId=c.getEmployeeId();
        int taskId=c.getTaskId();
        LocalDateTime taskDeadline = taskDao.getNextDeadline(taskId);
        String sqlInputCompletion ="INSERT INTO completion(completion_time, completion_comment, employee_id, task_id, task_deadline) VALUE(?,?,?,?,?)";
        getJdbcTemplate().update(sqlInputCompletion, time, comment, employeeId, taskId, taskDeadline);
    }

}
