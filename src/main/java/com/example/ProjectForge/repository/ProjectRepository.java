package com.example.ProjectForge.repository;

import com.example.ProjectForge.model.Project;
import com.example.ProjectForge.model.Subtask;
import com.example.ProjectForge.model.Task;
import com.example.ProjectForge.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProjectRepository implements IProjectRepository {

  @Autowired
private TaskRepository taskRepository;

public List<Project> getAllProjectsWithTasks() {
    List<Project> projects = getAllProjects();

 for (Project project : projects) {
    List<Task> tasks = taskRepository.getTaskByProjectID(project.getProject_id());
    project.setTasks(tasks);
    System.out.println("Tasks for project " + project.getProject_id() + ": " + tasks);
}
    return projects;
}

    //Get projects from user_id
    @Override
    public List<Project> getProjectsByID(int user_id) {
        List<Project> projects = new ArrayList<>();
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM project WHERE user_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int project_id = rs.getInt("project_id");
                String project_name = rs.getString("project_name");
                String project_description = rs.getString("project_description");
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();

                projects.add(new Project(project_id, project_name, project_description, start_date, end_date, user_id));
            }
            return projects;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Create projects
    @Override
    public void createProject(Project project, int user_id) {
        try {
            Connection con = ConnectionManager.getConnection();

            String SQL = "INSERT INTO project (project_name, project_description, start_date, end_date, user_id) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, project.getProject_name());
            pstmt.setString(2, project.getProject_description());
            pstmt.setObject(3, Date.valueOf(project.getStart_date()));
            pstmt.setObject(4, Date.valueOf(project.getEnd_date()));
            pstmt.setInt(5, user_id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int project_id = rs.getInt(1);
                project.setProject_id(project_id);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //Update project
    @Override
    public void editProject(Project project, int project_id, int user_id) {
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "UPDATE project SET project_name = ?, project_description = ?, start_date = ?, end_date = ? WHERE project_id = ? AND user_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
                pstmt.setString(1, project.getProject_name());
                pstmt.setString(2, project.getProject_description());
                pstmt.setObject(3, Date.valueOf(project.getStart_date()));
                pstmt.setObject(4, Date.valueOf(project.getEnd_date()));
                pstmt.setInt(5, project_id);
                pstmt.setInt(6, user_id);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    //Get project by project_id and user_id
    @Override
    public Project getProjectByIDs(int project_id, int user_id) {
        Project project = null;

        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM project WHERE project_id = ? AND user_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, project_id);
            pstmt.setInt(2, user_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String project_name = rs.getString("project_name");
                String project_description = rs.getString("project_description");
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();

                project = new Project(project_id, project_name, project_description, start_date, end_date, user_id);
            }
            return project;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Get project by project_id
    @Override
    public Project getProjectByProjectID(int project_id) {
        Project project = null;
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM project WHERE project_id = ?;";
            PreparedStatement statement = con.prepareStatement(SQL);
            statement.setInt(1, project_id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String project_name = rs.getString("project_name");
                String project_description = rs.getString("project_description");
                int user_id = rs.getInt("user_id");
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();

                project = new Project(project_id, project_name, project_description, start_date, end_date, user_id);
            }
            return project;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //delete project
    @Override
    public void deleteProject(int projectId) {
        try {
            Connection connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);

            //Delete subtask
            String SQL1 = "DELETE FROM subtask WHERE task_id IN (SELECT task_id FROM task WHERE project_id = ?)";
            PreparedStatement deleteSubtasksStatement = connection.prepareStatement(SQL1);
            deleteSubtasksStatement.setInt(1, projectId);
            deleteSubtasksStatement.executeUpdate();

            // Delete tasks
            String SQL2 = "DELETE FROM task WHERE project_id = ?";
            PreparedStatement deleteTasksStatement = connection.prepareStatement(SQL2);
            deleteTasksStatement.setInt(1, projectId);
            deleteTasksStatement.executeUpdate();

            // Delete project
            String SQL3 = "DELETE FROM project WHERE project_id = ?";
            PreparedStatement deleteProjectStatement = connection.prepareStatement(SQL3);
            deleteProjectStatement.setInt(1, projectId);
            deleteProjectStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Get project by task_id
    @Override
    public int getProjectID(int task_id) {
        int project_id = 0;
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT project_id from task WHERE task_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, task_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                project_id = rs.getInt("project_id");
            }
            return project_id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Get time for all tasks and subtasks
    @Override
    public Double getProjectTimeByProjectID(int project_id) {
        double estimatedTime = 0;
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT COALESCE(t.taskHours, 0) + COALESCE(SUM(s.hours), 0) AS totalTime " +
                    "FROM (SELECT task_id, COALESCE(SUM(hours), 0) AS taskHours FROM task WHERE project_id = ? GROUP BY task_id) AS t " +
                    "LEFT JOIN subtask AS s ON t.task_id = s.task_id " +
                    "GROUP BY t.task_id;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, project_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                estimatedTime += rs.getDouble("totalTime");
            }
            return estimatedTime;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Get all projects
 @Override
public List<Project> getAllProjects() {
    List<Project> projects = new ArrayList<>();
    try {
        Connection con = ConnectionManager.getConnection();
        String SQL = "SELECT * FROM project;";
        PreparedStatement pstmt = con.prepareStatement(SQL);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int project_id = rs.getInt("project_id");
            String project_name = rs.getString("project_name");
            String project_description = rs.getString("project_description");
            LocalDate start_date = rs.getDate("start_date").toLocalDate();
            LocalDate end_date = rs.getDate("end_date").toLocalDate();
            int user_id = rs.getInt("user_id");

            projects.add(new Project(project_id, project_name, project_description, start_date, end_date, user_id));
        }
        return projects;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}


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
                // Add other task fields here
                Task task = new Task();
                task.setTask_id(taskId);
                task.setTask_name(taskName);
                // Set other task fields here
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




    //old code
    /*
    @Override
public List<Project> getAllProjects() {
    List<Project> projects = new ArrayList<>();
    try {
        Connection con = ConnectionManager.getConnection();
        String SQL = "SELECT * FROM project;";
        PreparedStatement pstmt = con.prepareStatement(SQL);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int project_id = rs.getInt("project_id");
            String project_name = rs.getString("project_name");
            String project_description = rs.getString("project_description");
            LocalDate start_date = rs.getDate("start_date").toLocalDate();
            LocalDate end_date = rs.getDate("end_date").toLocalDate();
            int user_id = rs.getInt("user_id");

            projects.add(new Project(project_id, project_name, project_description, start_date, end_date, user_id));
        }
        return projects;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

     */
}
