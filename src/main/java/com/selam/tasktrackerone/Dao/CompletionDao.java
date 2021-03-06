package com.selam.tasktrackerone.Dao;

import com.selam.tasktrackerone.Mapper.CompletionMapper;
import com.selam.tasktrackerone.Model.Completion;
import com.selam.tasktrackerone.Model.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CompletionDao extends JdbcDaoSupport {
    @Autowired
    public CompletionDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private TaskDao taskDao;

    @Autowired
    static
    JdbcTemplate jdbcTemplate;

    public void inputCompletion(Completion c){
        //stores completion in database, and if completed task is periodical, links completion to relevant deadline.
        LocalDateTime time=c.getTime();
        String comment=c.getComment();
        String employeeUsername=c.getEmployeeUsername();
        int taskId=c.getTaskId();
        LocalDateTime taskDeadline = taskDao.getNextDeadline(taskId);
        String sqlInputCompletion ="INSERT INTO completion(completion_time, completion_comment, employee_Username, task_id, task_deadline) VALUE(?,?,?,?,?)";
        getJdbcTemplate().update(sqlInputCompletion, time, comment, employeeUsername, taskId, taskDeadline);
    }

    public List<Completion>getAllCompletions(LocalDateTime startTime, LocalDateTime endTime){//gets all completions after time
        endTime= endTime.plusDays(1);
        String sqlGetCompletions = "SELECT * FROM completion WHERE completion_time >= ? AND completion_time<?";
        List<Completion>completions= getJdbcTemplate().query(sqlGetCompletions, new Object[]{startTime, endTime}, new CompletionMapper());
        return completions;
    }
    public List<Wrapper> getWrappers(LocalDateTime startTime, LocalDateTime endTime){
        List<Wrapper> wrappers = new ArrayList<Wrapper>();
        List<Completion> completions = getAllCompletions(startTime, endTime);
        for (Completion completion : completions){
            Wrapper wrapper = new Wrapper();
            wrapper.setCompletion(completion);
            wrapper.setTask(taskDao.getTaskById(completion.getTaskId()));
            wrapper.setEmployee(employeeDao.getEmployeeByUsername(completion.getEmployeeUsername()));
            wrappers.add(wrapper);
        }
        return wrappers;
    }


}
