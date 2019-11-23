package com.selam.tasktrackerone.Dao;

import com.selam.tasktrackerone.Mapper.CompletionMapper;
import com.selam.tasktrackerone.Model.Completion;
import com.selam.tasktrackerone.Model.Employee;
import com.selam.tasktrackerone.Model.Task;
import com.selam.tasktrackerone.Model.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        Long employeeId=c.getEmployeeId();
        int taskId=c.getTaskId();
        LocalDateTime taskDeadline = taskDao.getNextDeadline(taskId);
        String sqlInputCompletion ="INSERT INTO completion(completion_time, completion_comment, employee_id, task_id, task_deadline) VALUE(?,?,?,?,?)";
        getJdbcTemplate().update(sqlInputCompletion, time, comment, employeeId, taskId, taskDeadline);
    }

    public List<Completion>getAllCompletions(LocalDateTime time){//gets all completions after time
        String sqlGetCompletions = "SELECT * FROM completion WHERE completion_time >= ?";
        return getJdbcTemplate().query(sqlGetCompletions, new Object[]{time}, new CompletionMapper());
    }
    public List<Wrapper> getWrappers(LocalDateTime time){
        List<Wrapper> wrappers = new ArrayList<Wrapper>();
        List<Completion> completions = getAllCompletions(time);
        for (Completion completion : completions){
            Wrapper wrapper = new Wrapper();
            wrapper.setCompletion(completion);
            wrapper.setTask(taskDao.getTaskById(completion.getTaskId()));
            wrapper.setEmployee(employeeDao.getEmployeeById(completion.getEmployeeId()));
            wrappers.add(wrapper);
        }
        return wrappers;
    }


}
