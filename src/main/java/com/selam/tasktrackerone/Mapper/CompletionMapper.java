package com.selam.tasktrackerone.Mapper;

import com.selam.tasktrackerone.Model.Completion;
import javafx.util.converter.TimeStringConverter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CompletionMapper implements RowMapper<Completion> {
    public static final String BASE_SQL = "Select c.completion_Id, c.completion_time, c.completion_comment, c.employee_id, c.task_id. c.task_deadline From completions c";

    @Override
    public Completion mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("completion_Id");
        //Time time = rs.getTime("completion_time");
        Timestamp time = rs.getTimestamp("completion_time");
        String comment = rs.getString("completion_comment");
        Long employeeId = rs.getLong("employee_id");
        int taskId = rs.getInt("task_id");
        Timestamp taskDeadline = rs.getTimestamp("task_deadline");
        return new Completion(id, time.toLocalDateTime(), comment, employeeId, taskId, taskDeadline.toLocalDateTime());
    }
}
