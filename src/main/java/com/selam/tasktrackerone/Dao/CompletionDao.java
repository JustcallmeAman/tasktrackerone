package com.selam.tasktrackerone.Dao;

import com.selam.tasktrackerone.Mapper.CompletionMapper;
import com.selam.tasktrackerone.Model.Completion;
import com.selam.tasktrackerone.Model.PeriodicalTask;
import com.selam.tasktrackerone.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Time;
import java.time.LocalTime;
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
    private TaskDao taskDao;

    @Autowired
    static
    JdbcTemplate jdbcTemplate;

    public void InputCompletion(Completion c){
        int id;
        LocalTime time=c.getTime();
        String comment=c.getComment();
        Long employeeId=c.getEmployeeId();
        int taskId=c.getTaskId();
        String sqlInputCompletion ="INSERT INTO completion(completion_time, completion_comment, employee_id, task_id) VALUE(?,?,?,?)";
        getJdbcTemplate().update(sqlInputCompletion, time, comment, employeeId, taskId);
        String sqlGetCompletionId= "SELECT id FROM completion WHERE completion_time = ?";
        id = getJdbcTemplate().queryForObject(sqlGetCompletionId,new Object[]{time}, Integer.class);
        String sqlGetTask = "SELECT task_taskType_id FROM tasks WHERE  id= ?";
        int taskType = getJdbcTemplate().queryForObject(sqlGetTask,new Object[]{taskId}, Integer.class);
        if (taskType == 1){//periodical
            //update deadlines table with completions
            LocalTime nextDeadline = taskDao.getNextPeriodicalDeadline(taskId);
            if ((time.isBefore(nextDeadline) && nextDeadline.isBefore(time.plusHours(1))) || (time.equals(nextDeadline)) || time.isAfter(nextDeadline)) {
                //if time is an hour or less before the deadline, or exactly on deadline, or past deadline,
                String sqlPutCompletionToDeadline = "UPDATE deadlines SET completion_id = ? WHERE task_id=? AND deadline_time = ?";
                getJdbcTemplate().update(sqlPutCompletionToDeadline, id, taskId, nextDeadline);
            }
        }
    }

}
