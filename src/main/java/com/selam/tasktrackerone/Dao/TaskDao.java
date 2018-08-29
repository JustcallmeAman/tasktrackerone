package com.selam.tasktrackerone.Dao;

import com.selam.tasktrackerone.Mapper.TaskMapper;
import com.selam.tasktrackerone.Model.Task;
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
        String taskFrequency = form.getFrequency();
        String taskDescription = form.getDescription();
        //int taskId = something + 1;

        getJdbcTemplate().update(sqlCreateTask, taskName, taskFrequency, taskDescription);

    }

    public List<Task> getAllTasks(){
        String sqlGetAllTaskId = "SELECT task_id FROM tasks";
        String sqlgetAllTasksbyId = "SELECT * FROM tasks WHERE task_id = ?";
        try {
            List<Integer> taskIds= getJdbcTemplate().queryForList(sqlGetAllTaskId,new Object[]{}, Integer.class);
            List<Task> tasks = new ArrayList<>();
            TaskMapper taskMapper = new TaskMapper();
            for (int taskid : taskIds){
                try{
                    tasks.add(getJdbcTemplate().queryForObject(sqlgetAllTasksbyId, new Object[]{taskid}, taskMapper));
                }
                catch (EmptyResultDataAccessException e){

                }
            }
            return tasks;
        }
        catch (Exception e){
            return null;
        }
    }
    public Task GetTaskById(int id){
        TaskMapper taskMapper= new TaskMapper();
        String sqlGetTaskById= "SELECT * FROM tasks Where task_id= ?";
        return getJdbcTemplate().queryForObject(sqlGetTaskById, new Object[]{id}, taskMapper);
    }


}
