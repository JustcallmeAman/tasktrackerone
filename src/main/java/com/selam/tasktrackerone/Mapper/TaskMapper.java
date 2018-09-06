package com.selam.tasktrackerone.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.selam.tasktrackerone.Model.Task;
import org.springframework.jdbc.core.RowMapper;

public class TaskMapper implements RowMapper<Task> {
    //used to translate between the tasks table and task objects

    public static final String BASE_SQL = "Select t.task_Id, t.task_name, t.task_frequency, t.task_description From tasks t";

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        int taskId = rs.getInt("task_Id");
        String taskName = rs.getString("task_name");
        String taskFrequency = rs.getString("task_frequency");
        String taskDescription = rs.getString("task_Description");

        return new Task(taskId, taskName,taskFrequency, taskDescription);
    }
}
