package com.selam.tasktrackerone.Dao;

import com.selam.tasktrackerone.Mapper.CompletionMapper;
import com.selam.tasktrackerone.Mapper.TaskMapper;
import com.selam.tasktrackerone.Model.Completion;
import com.selam.tasktrackerone.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Transactional
public class TaskDao extends JdbcDaoSupport{
    @Autowired
    public TaskDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Autowired
    static
    JdbcTemplate jdbcTemplate;

    public Task getTaskById(int id){
        TaskMapper taskMapper= new TaskMapper();
        String sqlGetTaskById= "SELECT * FROM tasks Where id= ?";
        return getJdbcTemplate().queryForObject(sqlGetTaskById, new Object[]{id}, taskMapper);
    }

    public List<Task> getAllTasks() {
        String sql= "SELECT * FROM tasks";
        try{
            List<Task> tasks=getJdbcTemplate().query(sql, new TaskMapper());
            for (Task task : tasks){
                task.setLastDone(getLastDone(task));
                task.setNextDeadline(getNextDeadline(task));
            }
            return  tasks;
        } catch (Exception e){
            return null;
        }
    }

    public int getTaskType(Task t){
        return getTaskType(t.getId());
    }

    public int getTaskType(int taskId){
        String sqlGetTaskType= "SELECT task_taskType_id FROM tasks WHERE id=?";
        int taskType = getJdbcTemplate().queryForObject(sqlGetTaskType,new Object[]{taskId}, Integer.class);
        return taskType;
    }

    public LocalDateTime getNextDeadline (Task task){
        LocalDateTime time;
        if (task.getType()==1){//periodical-next deadline depends on past deadline
            List<LocalDateTime> times = getPastDeadlines(task.getId());
            if(times.size()>0){
                time=times.get(times.size()-1);
                if (task.getLastDone().isBefore(time.plusHours(1))){//if task was last done at least 1 hr before next deadline, keep this deadline as not completed.
                    return time;
                }
            } else {
                time = LocalDateTime.now();
            }
        } else {//frequent- next deadline depends on last done
            time = getLastDone(task.getId());
        }
        if (time==null) {
            time=LocalDateTime.now();
        }
        LocalDateTime deadline= time.plusHours(task.getSequence());
        while(deadline.isBefore(LocalDateTime.now())){
           deadline=deadline.plusHours(task.getSequence());
        }
        return deadline;
    }

    public LocalDateTime getNextDeadline (int taskId){
        return getNextDeadline(getTaskById(taskId));
    }

    public List<LocalDateTime> getPastDeadlines(int taskId){
        String sqlGetTimes= "SELECT task_deadline FROM completion WHERE task_id=?";
        return getJdbcTemplate().queryForList(sqlGetTimes,new Object[]{taskId}, LocalDateTime.class);
    }

    public LocalDateTime getLastDone(Task task){
        return getLastDone(task.getId());
    }

    public LocalDateTime getLastDone(int taskId){
        String sqlGetLastDone = "SELECT completion_time FROM completion WHERE task_id=?";
        List<LocalDateTime> lastDoneTimes= getJdbcTemplate().queryForList(sqlGetLastDone,new Object[]{taskId}, LocalDateTime.class);
        if (lastDoneTimes.size()>0){//set last done time
            return lastDoneTimes.get(lastDoneTimes.size()-1);
        } else { return null;}
    }

    public void editTask(Task task){
        String sqlEditTask= "UPDATE tasks SET task_name=? WHERE id=?";
        getJdbcTemplate().update(sqlEditTask, task.getName(),task.getId());
    }

    public void deleteTask(Task task){
        String sqlDeleteTask= "DELETE FROM tasks WHERE id=?";
        getJdbcTemplate().update(sqlDeleteTask, task.getId());
    }

    public void addTask (Task task){
        String sqlAddTask = "INSERT INTO tasks (task_name, task_description, task_taskType_id, task_sequence) VALUES (?, ?, ?, ?)";
        getJdbcTemplate().update(sqlAddTask, task.getName(), task.getDescription(), task.getType(), task.getSequence());
    }

    public List<Completion> getCompletions(int taskId){
        String sqlGetCompletionsForTask = "SELECT * FROM completion WHERE task_id=?";
        return getJdbcTemplate().query(sqlGetCompletionsForTask, new CompletionMapper());
    }

    public Long getLastCompletor(int taskId){
        List<Completion> completions= getCompletions(taskId);
        return completions.get(completions.size()-1).getEmployeeId();
    }

}
