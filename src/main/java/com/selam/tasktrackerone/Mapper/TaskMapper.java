package com.selam.tasktrackerone.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.selam.tasktrackerone.Model.Task;
import org.springframework.jdbc.core.RowMapper;

public class TaskMapper implements RowMapper<Task> {
    //used to translate between the tasks table and task objects

    public static final String BASE_SQL = "Select t.Id, t.task_name, t.task_description, t.task_taskType_id, task_sequence From tasks t";
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        int taskId = rs.getInt("Id");
        String taskName = rs.getString("task_name");
        String taskDescription = rs.getString("task_Description");
        int taskType = rs.getInt("task_taskType_id");
        int sequence= rs.getInt("task_sequence");
        LocalDateTime lt1 = LocalDateTime.now();//placeholder for localtime in deadlines and last dones.
        return new Task(taskId, taskName, taskDescription, taskType, lt1, lt1, sequence);
    }
}
