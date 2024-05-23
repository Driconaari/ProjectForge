package com.example.ProjectForge.repository;

import com.example.ProjectForge.dto.TaskSubtaskDTO;
import com.example.ProjectForge.model.Subtask;
import com.example.ProjectForge.model.Task;
import com.example.ProjectForge.util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TaskRepository implements ITaskRepository {


    //Get Task by
    @Override
    public List<Task> getTaskByProjectID(int project_id) {
        List<Task> tasks = new ArrayList<>();

        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM task WHERE project_id = ?;";
            System.out.println("SQL Query: " + SQL); // Print the SQL query

            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, project_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int task_id = rs.getInt("task_id");
                String task_name = rs.getString("task_name");
                Double hours = rs.getDouble("hours");
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();
                int status = rs.getInt("status");

                tasks.add(new Task(task_id, task_name, hours, start_date, end_date, status, project_id));
            }

            System.out.println("Tasks: " + tasks); // Print the tasks
            return tasks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Create Task
    @Override
    public void createTask(Task task, int project_id) {
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "INSERT INTO task (task_name, hours, start_date, end_date, status, project_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, task.getTask_name());
            pstmt.setDouble(2, task.getHours());
            pstmt.setObject(3, Date.valueOf(task.getStart_date()));
            pstmt.setObject(4, Date.valueOf(task.getEnd_date()));
            pstmt.setInt(5, task.getStatus());
            pstmt.setInt(6, project_id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int task_id = rs.getInt(1);
                task.setTask_id(task_id);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Edit task
    @Override
    public void editTask(Task task, int task_id, int project_id) {
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "UPDATE task SET task_name = ?, hours = ?, start_date = ?, end_date = ?, status = ? WHERE task_id = ? AND project_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
                pstmt.setString(1, task.getTask_name());
                pstmt.setDouble(2, task.getHours());
                pstmt.setObject(3, Date.valueOf(task.getStart_date()));
                pstmt.setObject(4, Date.valueOf(task.getEnd_date()));
                pstmt.setInt(5, task.getStatus());
                pstmt.setInt(6, task_id);
                pstmt.setInt(7, project_id);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    //Get project from user_id and user_id
    @Override
    public Task getTaskByIDs(int task_id, int project_id) {
        Task task = null;
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM task WHERE task_id = ? AND project_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, task_id);
            pstmt.setInt(2, project_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String task_name = rs.getString("task_name");
                Double hours = rs.getDouble("hours");
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();
                int status = rs.getInt("status");

                task = new Task(task_id, task_name, hours, start_date, end_date, status, project_id);
            }
            return task;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Delete Task
    @Override
    public void deleteTask(int task_id) {
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "DELETE FROM subtask WHERE task_id = ?";
            String SQL1 = "DELETE FROM task WHERE task_id = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            PreparedStatement pstmt1 = con.prepareStatement(SQL1);
            pstmt.setInt(1, task_id);
            pstmt.executeUpdate();
            pstmt1.setInt(1, task_id);
            pstmt1.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Get Task from task_id
    @Override
    public Task getTaskbyTaskId(int task_id) {
        Task task = null;
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM task WHERE task_id = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, task_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String task_name = rs.getString("task_name");
                Double hours = rs.getDouble("hours");
                int project_id = rs.getInt("project_id");
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();
                int status = rs.getInt("status");

                task = new Task(task_id, task_name, hours, start_date, end_date, status, project_id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return task;
    }

    //Get project_id by task_id
    @Override
    public int getProjectIDbyTaskID(int task_id) {
        int project_id = 0;
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT project_id FROM task WHERE task_id = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, task_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                project_id = rs.getInt("project_id");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return project_id;
    }

    //Calculated time for task and subtask
    @Override
    public Double getProjectTimeByTaskID(int task_id) {
        double estimatedTime = 0;
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT COALESCE(t.hours, 0) + COALESCE(SUM(s.hours), 0) AS totalTime " +
                    "FROM task AS t\n" +
                    "LEFT JOIN subtask AS s USING(task_id)\n" +
                    "WHERE t.task_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, task_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                estimatedTime = rs.getDouble("totalTime");
            }

            return estimatedTime;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Get task with subtasks by project_id for ganttmodel in frontend (TaskSubtaskDTO) - used in TaskController for getTaskSubtasksByProID method in frontend (ganttmodel.vue) - getTaskSubtasksByProID
    @Override
    public List<TaskSubtaskDTO> getTaskSubtasksByProID(int project_id) {
        List<TaskSubtaskDTO> taskSubtaskList = new ArrayList<>();
        int currentTaskId = -1;
        TaskSubtaskDTO currentTask = null;

        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT t.task_id, t.task_name, t.hours AS task_hours, t.start_date AS task_start_date, t.end_date AS task_end_date, t.status AS task_status, t.project_id AS task_project_id, " +
                    "s.subtask_id, s.subtask_name, s.hours AS subtask_hours, s.start_date AS subtask_start_date, s.end_date AS subtask_end_date, s.status AS subtask_status, s.task_id AS subtask_task_id " +
                    "FROM task AS t " +
                    "LEFT JOIN subtask AS s ON t.task_id = s.task_id " +
                    "WHERE t.project_id = ? " +
                    "ORDER BY t.task_id, s.subtask_id;";

            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, project_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int task_id = rs.getInt("task_id");
                if (task_id != currentTaskId) {
                    // Create a new TaskSubtaskDTO of the task
                    String task_name = rs.getString("task_name");
                    Double task_hours = rs.getDouble("task_hours");
                    LocalDate task_start_date = rs.getDate("task_start_date") != null ? rs.getDate("task_start_date").toLocalDate() : null;
                    LocalDate task_end_date = rs.getDate("task_end_date") != null ? rs.getDate("task_end_date").toLocalDate() : null;
                    int task_status = rs.getInt("task_status");

                    currentTask = new TaskSubtaskDTO(task_id, task_name, task_hours, task_start_date, task_end_date, task_status, project_id, new ArrayList<>());
                    taskSubtaskList.add(currentTask);
                    currentTaskId = task_id;
                }

                // Create subtask and add to subtask list
                int subtask_id = rs.getInt("subtask_id");
                if (subtask_id >= 0) {
                    String subtask_name = rs.getString("subtask_name");
                    Double subtask_hours = rs.getDouble("subtask_hours");
                    LocalDate subtask_start_date = rs.getDate("subtask_start_date") != null ? rs.getDate("subtask_start_date").toLocalDate() : null;
                    LocalDate subtask_end_date = rs.getDate("subtask_end_date") != null ? rs.getDate("subtask_end_date").toLocalDate() : null;
                    int subtask_status = rs.getInt("subtask_status");
                    int subtask_task_id = rs.getInt("subtask_task_id");

                    if (currentTask != null) {
                        List<Subtask> subtasks = currentTask.getSubtasks();
                        subtasks.add(new Subtask(subtask_id, subtask_name, subtask_hours, subtask_start_date, subtask_end_date, subtask_status, subtask_task_id));
                    }
                }
            }
            return taskSubtaskList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

// Get tasks with subtasks by project_id for ganttmodel in frontend (TaskSubtaskDTO) - used in TaskController for getTasksWithSubtasks method in frontend (ganttmodel.vue) - getTasksWithSubtasks
    @Override
    public List<Task> getTasksWithSubtasksByProjectID(int projectId) {
        List<Task> tasks = new ArrayList<>();
        Map<Integer, Task> taskMap = new HashMap<>();

        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM task LEFT JOIN subtask ON task.task_id = subtask.task_id WHERE task.project_id = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int taskId = rs.getInt("task.task_id");


                if (!taskMap.containsKey(taskId)) {
                    String taskName = rs.getString("task.task_name");
                    Double hours = rs.getDouble("task.hours");
                    LocalDate startDate = rs.getDate("task.start_date").toLocalDate();
                    LocalDate endDate = rs.getDate("task.end_date").toLocalDate();
                    int status = rs.getInt("task.status");

                    Task task = new Task();
                    task.setTask_id(taskId);
                    task.setTask_name(taskName);
                    task.setHours(hours);
                    task.setStart_date(startDate);
                    task.setEnd_date(endDate);
                    task.setStatus(status);

                    tasks.add(task);
                    taskMap.put(taskId, task);
                }

                int subtaskId = rs.getInt("subtask.subtask_id");
                if (subtaskId > 0) {
                    String subtaskName = rs.getString("subtask.subtask_name");
                    // Add other subtask fields here
                    Subtask subtask = new Subtask();
                    subtask.setSubtask_id(subtaskId);
                    subtask.setSubtask_name(subtaskName);
                    // Set other subtask fields here
                    taskMap.get(taskId).getSubtasks().add(subtask);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tasks;
    }


    //Get task with subtasks by task_id and project_id for edit task page in frontend (TaskSubtaskDTO) - used in TaskController
    // for editTask method in frontend (editTask.vue) - getTaskWithSubtasks
    //used for ganttmodel in frontend

   /* public Task getTaskWithSubtasks(int taskId) {
        Task task = null;
        List<Subtask> subtasks = new ArrayList<>();

        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM task WHERE task_id = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, taskId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String task_name = rs.getString("task_name");
                Double hours = rs.getDouble("hours");
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();
                int status = rs.getInt("status");
                int project_id = rs.getInt("project_id");

                task = new Task(taskId, task_name, hours, start_date, end_date, status, project_id);
            }

            SQL = "SELECT * FROM subtask WHERE task_id = ?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, taskId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int subtask_id = rs.getInt("subtask_id");
                String subtask_name = rs.getString("subtask_name");
                Double subtask_hours = rs.getDouble("hours");
                LocalDate subtask_start_date = rs.getDate("start_date").toLocalDate();
                LocalDate subtask_end_date = rs.getDate("end_date").toLocalDate();
                int subtask_status = rs.getInt("status");

                Subtask subtask = new Subtask(subtask_id, subtask_name, subtask_hours, subtask_start_date, subtask_end_date, subtask_status, taskId);
                subtasks.add(subtask);
            }

            if (task != null) {
                task.setSubtasks(subtasks);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return task;
    }

    */
}
