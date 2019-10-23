package com.selam.tasktrackerone.Dao;

import com.selam.tasktrackerone.Mapper.TaskMapper;
import com.selam.tasktrackerone.Model.FrequentTask;
import com.selam.tasktrackerone.Model.PeriodicalTask;
import com.selam.tasktrackerone.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

@Repository
@Transactional
public class TaskDao extends JdbcDaoSupport{
    @Autowired
    public TaskDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
    private static List<String> taskList;

    @Autowired
    static
    JdbcTemplate jdbcTemplate;

    public void createTask(Task form){

        String sqlCreateTask ="INSERT INTO tasks (task_name, task_description, task_taskType_id) VALUE(?,?,?)";
        String taskName = form.getName();
        int type = form.getType();
        String taskDescription = form.getDescription();

        getJdbcTemplate().update(sqlCreateTask, taskName, taskDescription, type);
    }

    public Task assignType(Task task){
        String sqlGetTaskByType= "SELECT task_taskType_id FROM tasks Where task_taskType_id= ?";
        int taskType= getJdbcTemplate().queryForObject(sqlGetTaskByType,new Object[]{task.getId()}, Integer.class);//get int tasktype for given task
        if (taskType == 2){
            //set frequency, return frequenttask
            FrequentTask frequentTask = (FrequentTask) task;
            String sqlGetTaskFrequency= "SELECT taskfrequency_frequency FROM taskfrequencies Where task_id= ?";
            Long frequency= getJdbcTemplate().queryForObject(sqlGetTaskFrequency,new Object[]{frequentTask.getId()}, Long.class);//get frequency of a frequenttask
            frequentTask.setFrequency(frequency);
            return frequentTask;
        }
        else if (taskType==1){
            //set deadlines, return periodicaltask
            PeriodicalTask periodicalTask = (PeriodicalTask) task;
            String sqlGetTaskDeadlines= "SELECT taskperiod_period FROM taskperiods Where task_id= ?";
            List<LocalTime> deadlines= getJdbcTemplate().queryForList(sqlGetTaskDeadlines,new Object[]{periodicalTask.getId()}, LocalTime.class);//get int tasktype for given task
            periodicalTask.setDeadlines(deadlines);
            return periodicalTask;
        }
        return null;
    }

    public Task getTaskById(int id){
        TaskMapper taskMapper= new TaskMapper();
        String sqlGetTaskById= "SELECT * FROM tasks Where task_id= ?";
        return getJdbcTemplate().queryForObject(sqlGetTaskById, new Object[]{id}, taskMapper);
    }

    public List<Task> getAllTasks() {
        String sql= "SELECT * FROM tasks";
        try{
            List<Task> tasks=getJdbcTemplate().query(sql, new TaskMapper());
            return  tasks;
        } catch (Exception e){
            return null;
        }
    }

    public int getTaskType(Task t){
        String sqlGetTaskType= "SELECT task_taskType_id FROM tasks WHERE id=?";
        int taskType = getJdbcTemplate().queryForObject(sqlGetTaskType,new Object[]{t.getId()}, Integer.class);
        return taskType;
    }

    public int getTaskType(int taskId){
        String sqlGetTaskType= "SELECT task_taskType_id FROM tasks WHERE id=?";
        int taskType = getJdbcTemplate().queryForObject(sqlGetTaskType,new Object[]{taskId}, Integer.class);
        return taskType;
    }

    public LocalTime getNextPeriodicalDeadline (Task t){
        String sqlGetNextDeadline = "SELECT deadline_time FROM deadlines WHERE completion_id IS NULL AND task_id=?";
        List<LocalTime> deadlines= getJdbcTemplate().queryForList(sqlGetNextDeadline,new Object[]{t.getId()}, LocalTime.class);
        if (deadlines.size()==0){//if all deadlines are met for the day,
            String sqlGetNextDeadlineTomorrow = "SELECT deadline_time FROM deadlines WHERE task_id=?";
            List<LocalTime> tomorrowDeadlines= getJdbcTemplate().queryForList(sqlGetNextDeadlineTomorrow,new Object[]{t.getId()}, LocalTime.class);
            Collections.sort(tomorrowDeadlines);
            return (tomorrowDeadlines.get(0));
        }
        else {
            Collections.sort(deadlines);
            return (deadlines.get(0));
        }
    }

    public LocalTime getNextPeriodicalDeadline (int taskid){
        String sqlGetNextDeadline = "SELECT deadline_time FROM deadlines WHERE completion_id IS NULL AND task_id=?";
        List<LocalTime> deadlines= getJdbcTemplate().queryForList(sqlGetNextDeadline,new Object[]{taskid}, LocalTime.class);
        if (deadlines.size()==0){//if all deadlines are met for the day,
            String sqlGetNextDeadlineTomorrow = "SELECT deadline_time FROM deadlines WHERE task_id=?";
            List<LocalTime> tomorrowDeadlines= getJdbcTemplate().queryForList(sqlGetNextDeadlineTomorrow,new Object[]{taskid}, LocalTime.class);
            Collections.sort(tomorrowDeadlines);
            return (tomorrowDeadlines.get(0));
        }
        else {
            Collections.sort(deadlines);
            return (deadlines.get(0));
        }

    }

    public Duration getFrequency(int taskid){
        String sqlGetFrequency="SELECT taskfrequency_frequency FROM taskfrequencies WHERE task_id=?";
        Duration frequency= Duration.ofHours(getJdbcTemplate().queryForObject(sqlGetFrequency,new Object[]{taskid}, Integer.class));
        return frequency;
    }

    public Duration getFrequency(Task task){
        int taskid= task.getId();
        String sqlGetFrequency="SELECT taskfrequency_frequency FROM taskfrequencies WHERE task_id=?";
        Duration frequency= Duration.ofHours(getJdbcTemplate().queryForObject(sqlGetFrequency,new Object[]{taskid}, Integer.class));
        return frequency;
    }

    public LocalTime getLastDoneTime(Task t){
        String sqlGetDoneTimes= "SELECT completion_time FROM completion WHERE task_id=?";
        List<LocalTime> doneTimes= getJdbcTemplate().queryForList(sqlGetDoneTimes,new Object[]{t.getId()}, LocalTime.class);//get times that task t hs been completed so far
        LocalTime lastDone=null;
        if(!doneTimes.isEmpty()){
            //doneTimes.sort(Comparator.naturalOrder()); //might be important if for some reason this is not a ordered list already
            lastDone=doneTimes.get(doneTimes.size()-1);
        }
        return lastDone;
    }

    public LocalTime getLastDoneTime(int taskId){
        String sqlGetDoneTimes= "SELECT completion_time FROM completion WHERE task_id=?";
        List<LocalTime> doneTimes= getJdbcTemplate().queryForList(sqlGetDoneTimes,new Object[]{taskId}, LocalTime.class);//get times that task t hs been completed so far
        LocalTime lastDone=null;
        if(!doneTimes.isEmpty()){
            //doneTimes.sort(Comparator.naturalOrder()); //might be important if for some reason this is not a ordered list already
            lastDone=doneTimes.get(doneTimes.size()-1);
        }
        return lastDone;
    }
    public void editTask(Task task){
        String sqlEditTask= "UPDATE tasks SET task_name=? WHERE id=?";
        getJdbcTemplate().update(sqlEditTask, task.getName(),task.getId());

    }
    public void deleteTask(Task task){
        String sqlDeleteTask= "DELETE FROM tasks WHERE id=?";
        getJdbcTemplate().update(sqlDeleteTask, task.getId());
    }

}
