package com.example.ProjectForge.repository;

import com.example.ProjectForge.model.Subtask;
import com.example.ProjectForge.util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubtaskRepositoryDB implements ISubtaskRepository {

    //Get subtasks from project_id
    @Override
    public List<Subtask> getSubtasksByTaskID(int task_id) {
        List<Subtask> subtasks = new ArrayList<>();
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL1 = "SELECT * FROM subtask WHERE task_id = ?;";
            PreparedStatement pstmt1 = con.prepareStatement(SQL1);
            pstmt1.setInt(1, task_id);
            ResultSet rs = pstmt1.executeQuery();

            while (rs.next()) {
                int subtask_id = rs.getInt("subtask_id");
                String subtask_name = rs.getString("subtask_name");
                double hours = rs.getDouble("hours");
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();
                int status = rs.getInt("status");

                subtasks.add(new Subtask(subtask_id, subtask_name, hours, start_date, end_date, status, task_id));
            }
            return subtasks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    //Create subtask to project
    @Override
    public Subtask createSubtask(Subtask subtask, int task_id) {
        Subtask createdSubtask = null;
        try {
            Connection conn = ConnectionManager.getConnection();

            String SQL = "INSERT INTO subtask (subtask_name, hours, start_date, end_date, status, task_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, subtask.getSubtask_name());
            pstmt.setDouble(2, subtask.getHours());
            pstmt.setObject(3, Date.valueOf(subtask.getStart_date()));
            pstmt.setObject(4, Date.valueOf(subtask.getEnd_date()));
            pstmt.setInt(5, subtask.getStatus());
            pstmt.setInt(6, task_id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int subtask_id = rs.getInt(1);
                subtask.setSubtask_id(subtask_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return createdSubtask;
    }

    //Edit subtask
    @Override
    public void editSubtask(Subtask subtask, int subtask_id, int task_id) {
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "UPDATE subtask SET subtask_name = ?, hours = ?, start_date = ?, end_date = ?, status = ? WHERE subtask_id = ? AND task_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
                pstmt.setString(1, subtask.getSubtask_name());
                pstmt.setDouble(2, subtask.getHours());
                pstmt.setObject(3, Date.valueOf(subtask.getStart_date()));
                pstmt.setObject(4, Date.valueOf(subtask.getEnd_date()));
                pstmt.setInt(5, subtask.getStatus());
                pstmt.setInt(6, subtask_id);
                pstmt.setInt(7, task_id);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    //Get subtask from subtask_ and task_id
    @Override
    public Subtask getSubtaskByIDs(int subtask_id, int task_id) {
        Subtask subtask = null;

        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM subtask WHERE subtask_id = ? AND task_id = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, subtask_id);
            pstmt.setInt(2, task_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String subtask_name = rs.getString("subtask_name");
                Double hours = rs.getDouble("hours");
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();
                int status = rs.getInt("status");

                subtask = new Subtask(subtask_id, subtask_name, hours, start_date, end_date, status, task_id);
            }
            return subtask;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Delete subtask
    @Override
    public void deleteSubtask(int subtask_id) {
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "DELETE FROM subtask WHERE subtask_id = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, subtask_id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Get subtask by id
    @Override
    public Subtask getSubtaskByID(int subtask_id) {
        Subtask subtask = null;
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM subtask WHERE subtask_id = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, subtask_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String subtask_name = rs.getString("subtask_name");
                double hours = rs.getDouble("hours");
                int task_id = rs.getInt("task_id");
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();
                int status = rs.getInt("status");

                subtask = new Subtask(subtask_id, subtask_name, hours, start_date, end_date, status, task_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subtask;
    }


}
