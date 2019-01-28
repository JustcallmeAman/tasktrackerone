package com.selam.tasktrackerone.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.selam.tasktrackerone.Model.FrequentTask;
import com.selam.tasktrackerone.Model.PeriodicalTask;
import com.selam.tasktrackerone.Model.Task;
import org.springframework.jdbc.core.RowMapper;

public class TaskMapper implements RowMapper<Task> {
    //used to translate between the tasks table and task objects

    public static final String BASE_SQL = "Select t.Id, t.task_name, t.task_description, t.task_taskType_id From tasks t";
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        int taskId = rs.getInt("Id");
        String taskName = rs.getString("task_name");
        String taskDescription = rs.getString("task_Description");
        int taskType = rs.getInt("task_taskType_id");
        String taskFrequencyDescription="";
        LocalTime lt1 = LocalTime.of(00, 00, 00, 0000);//placeholder for localtime in deadlines and last dones.
        LocalTime lt2 = LocalTime.of(05, 00, 00, 0000);
        List<LocalTime> deadlines = new ArrayList<>();
        if (taskType==1){//periodical
            return new PeriodicalTask(taskId, taskName,taskFrequencyDescription, taskDescription, lt1, lt2, deadlines);
        }
        else{//frequent
            return new FrequentTask(taskId, taskName,taskFrequencyDescription, taskDescription, lt1, lt2 , 1L);
        }

    }
}
