package com.selam.tasktrackerone.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.selam.tasktrackerone.Model.FrequentTask;
import com.selam.tasktrackerone.Model.Task;
import org.springframework.jdbc.core.RowMapper;

public class TaskMapper implements RowMapper<Task> {
    //used to translate between the tasks table and task objects

    public static final String BASE_SQL = "Select t.Id, t.task_name, t.task_frequencyDescription, t.task_description, t.task_taskType_id From tasks t";
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        int taskId = rs.getInt("Id");
        String taskName = rs.getString("task_name");
        String taskFrequencyDescription = rs.getString("task_frequencyDescription");
        String taskDescription = rs.getString("task_Description");
        int taskType = rs.getInt("task_taskType_id");

        return new Task(taskId, taskName,taskFrequencyDescription, taskDescription);
    }
}
