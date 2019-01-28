package com.selam.tasktrackerone.Dao;

import com.selam.tasktrackerone.Mapper.TaskMapper;
import com.selam.tasktrackerone.Model.FrequentTask;
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

        String sqlCreateTask ="INSERT INTO tasks (task_name, task_frequency, task_description) VALUE(?,?,?)";
        String taskName = form.getName();
        String taskFrequency = form.getFrequencyDescription();
        String taskDescription = form.getDescription();

        getJdbcTemplate().update(sqlCreateTask, taskName, taskFrequency, taskDescription);
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

    public Task AssignType(Task task){
        TaskMapper taskMapper= new TaskMapper();
        String sqlGetTaskByType= "SELECT task_taskType_id FROM tasks Where task_taskType_id= ?";
        int taskType= getJdbcTemplate().queryForObject(sqlGetTaskByType,new Object[]{task.getId()}, Integer.class);//get int tasktype for given task
        if (taskType == 1){
            //set frequency, return frequenttask
            FrequentTask frequentTask = (FrequentTask) task;
            String sqlGetTaskFrequency= "SELECT taskfrequency_frequency FROM taskfrequencies Where task_id= ?";
            Long frequency= getJdbcTemplate().queryForObject(sqlGetTaskFrequency,new Object[]{frequentTask.getId()}, Long.class);//get frequency of a frequenttask
            frequentTask.setFrequency(frequency);
            return frequentTask;
        }
        else {
            //set deadlines, return periodicaltask
            PeriodicalTask periodicalTask = (PeriodicalTask) task;
            String sqlGetTaskDeadlines= "SELECT taskperiod_period FROM taskperiods Where task_id= ?";
            List<LocalTime> deadlines= getJdbcTemplate().queryForList(sqlGetTaskDeadlines,new Object[]{periodicalTask.getId()}, LocalTime.class);//get int tasktype for given task
            periodicalTask.setDeadlines(deadlines);
            return periodicalTask;
        }
    }

    public Task GetTaskById(int id){
        TaskMapper taskMapper= new TaskMapper();
        String sqlGetTaskById= "SELECT * FROM tasks Where task_id= ?";
        return getJdbcTemplate().queryForObject(sqlGetTaskById, new Object[]{id}, taskMapper);
    }
    public LocalTime GetLastDoneTime(Task t){
        String sqlGetDoneTimes= "SELECT completion_time FROM completion WHERE task_id=?";
        List<LocalTime> doneTimes= getJdbcTemplate().queryForList(sqlGetDoneTimes,new Object[]{t.getId()}, LocalTime.class);//get times that task t hs been completed so far
        LocalTime lastDone=null;
        if(!doneTimes.isEmpty()){
            //doneTimes.sort(Comparator.naturalOrder()); //might be important if for some reason this is not a ordered list already
            lastDone=doneTimes.get(doneTimes.size()-1);
        }
        return lastDone;

    }



}
