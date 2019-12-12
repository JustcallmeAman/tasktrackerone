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
        try{
            TaskMapper taskMapper= new TaskMapper();
            String sqlGetTaskById= "SELECT * FROM tasks Where id= ?";
            return getJdbcTemplate().queryForObject(sqlGetTaskById, new Object[]{id}, taskMapper);
        } catch (Exception e){
            return null;
        }
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
            return new ArrayList<Task>();
        }
    }

    public List<Task> getAllEnabledTasks() {
        String sql= "SELECT * FROM tasks WHERE enabled =1";
        try{
            List<Task> tasks=getJdbcTemplate().query(sql, new TaskMapper());
            for (Task task : tasks){
                task.setLastDone(getLastDone(task));
                task.setNextDeadline(getNextDeadline(task));
            }
            return  tasks;
        } catch (Exception e){
            return new ArrayList<Task>();
        }
    }

    public int getTaskStatus(Task task){
        String sqlTaskStatus = "SELECT enabled FROM tasks WHERE id=?";
        try{
            int status = getJdbcTemplate().queryForObject(sqlTaskStatus,new Object[]{task.getId()}, Integer.class);
            return status;
        } catch (Exception e){
            return -1;
        }
    }

    public int getTaskType(Task t){
        return getTaskType(t.getId());
    }

    public int getTaskType(int taskId){
        String sqlGetTaskType= "SELECT task_taskType_id FROM tasks WHERE id=?";
        try{
            int taskType = getJdbcTemplate().queryForObject(sqlGetTaskType,new Object[]{taskId}, Integer.class);
            return taskType;
        } catch (Exception e){
            return -1;
        }
    }

    public LocalDateTime getNextDeadline (Task task){
        LocalDateTime time;
        try{
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
        } catch(Exception e){
            return null;
        }
    }

    public LocalDateTime getNextDeadline (int taskId){
        return getNextDeadline(getTaskById(taskId));
    }

    public List<LocalDateTime> getPastDeadlines(int taskId){
        try{
            String sqlGetTimes= "SELECT task_deadline FROM completion WHERE task_id=?";
            return getJdbcTemplate().queryForList(sqlGetTimes,new Object[]{taskId}, LocalDateTime.class);
        } catch(Exception e){
            return null;
        }
    }

    public LocalDateTime getLastDone(Task task){
        return getLastDone(task.getId());
    }

    public LocalDateTime getLastDone(int taskId){
        try {
            String sqlGetLastDone = "SELECT completion_time FROM completion WHERE task_id=?";
            List<LocalDateTime> lastDoneTimes= getJdbcTemplate().queryForList(sqlGetLastDone,new Object[]{taskId}, LocalDateTime.class);
            if (lastDoneTimes.size()>0){//set last done time
                return lastDoneTimes.get(lastDoneTimes.size()-1);
            } else { return null;}
        } catch (Exception e){
            return null;
        }
    }

    public void editTask(Task task){
        try{
            String sqlEditTask= "UPDATE tasks SET task_name=?, task_description=? WHERE id=?";
            Object[] vals = new Object[]{task.getName(), task.getDescription(), task.getId()};
            getJdbcTemplate().update(sqlEditTask, vals);
        } catch (Exception e) {

        }
    }

    public void disableTask(Task task){
        try{
            String sqlDisableTask= "UPDATE tasks SET enabled = 0 WHERE id=?";
            getJdbcTemplate().update(sqlDisableTask, task.getId());
        } catch(Exception e) {

        }
    }

    public void enableTask(Task task){
        try{
            String sqlDisableTask= "UPDATE tasks SET enabled = 1 WHERE id=?";
            getJdbcTemplate().update(sqlDisableTask, task.getId());
        } catch(Exception e) {

        }
    }

    public void addTask (Task task){
        try{
            String sqlAddTask = "INSERT INTO tasks (task_name, task_description, task_taskType_id, task_sequence) VALUES (?, ?, ?, ?)";
            getJdbcTemplate().update(sqlAddTask, task.getName(), task.getDescription(), task.getType(), task.getSequence());
        } catch(Exception e) {

        }
    }

    public List<Completion> getCompletions(int taskId){
        try{
            String sqlGetCompletionsForTask = "SELECT * FROM completion WHERE task_id=?";
            return getJdbcTemplate().query(sqlGetCompletionsForTask, new Object[]{taskId}, new CompletionMapper());
        } catch (Exception e){
            return null;
        }
    }



}
