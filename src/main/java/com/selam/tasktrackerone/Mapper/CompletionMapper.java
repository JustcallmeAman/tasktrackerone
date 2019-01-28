package com.selam.tasktrackerone.Mapper;

import com.selam.tasktrackerone.Model.Completion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

public class CompletionMapper implements RowMapper<Completion> {
    public static final String BASE_SQL = "Select c.completion_Id, c.completion_time, c.completion_comment, c.employee_id, c.task_id From completions c";

    @Override
    public Completion mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("completion_Id");
        Time time = rs.getTime("completion_time");
        String comment = rs.getString("completion_comment");
        Long employeeId = rs.getLong("employee_id");
        int taskId = rs.getInt("task_id");
        return new Completion(id, time.toLocalTime(), comment, employeeId, taskId);
    }
}
