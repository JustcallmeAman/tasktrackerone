package com.selam.tasktrackerone.Dao;

import com.selam.tasktrackerone.Model.Completion;
import com.selam.tasktrackerone.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.sql.Time;
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
        LocalTime time=c.getTime();
        String comment=c.getComment();
        Long employeeId=c.getEmployeeId();
        int taskId=c.getTaskId();
        String sqlInputCompletion ="INSERT INTO completion(completion_time, completion_comment, employee_id, task_id) VALUE(?,?,?,?)";
        getJdbcTemplate().update(sqlInputCompletion, time, comment, employeeId, taskId);
        String sqlGetCompletionId= "SELECT id FROM completion WHERE completion_time = ?";
        int id = getJdbcTemplate().queryForObject(sqlGetCompletionId,new Object[]{time}, Integer.class);
        int taskType = taskDao.getTaskType(taskId);
        if (taskType == 1){//periodical
            //update deadlines table with completions
            LocalTime nextDeadline = taskDao.getNextPeriodicalDeadline(taskId);
            if (time.equals(nextDeadline.minusHours(1)) || time.isAfter(nextDeadline.minusHours(1))){
                //if time is greater on or after an hour or before the deadline, match completion to next deadline.
                String sqlPutCompletionToDeadline = "UPDATE deadlines SET completion_id = ? WHERE task_id=? AND deadline_time = ?";
                getJdbcTemplate().update(sqlPutCompletionToDeadline, id, taskId, nextDeadline);
            }
        }

    }

}
