package com.example.projectforge.dto;

import com.example.projectforge.model.Subtask;

import java.time.LocalDate;
import java.util.List;

public class TaskSubtaskDTO {

    private int id;
    private String name;
    private double hours;
    private LocalDate start_date;
    private LocalDate end_date;
    private int status;
    private double calculatedTime;
    private int project_id;
    private List<Subtask> subtasks;

    public TaskSubtaskDTO(int id, String name, double hours, LocalDate start_date, LocalDate end_date, int status, int project_id, List<Subtask> subtasks) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.project_id = project_id;
        this.subtasks = subtasks;
    }

    public TaskSubtaskDTO(int id, String name, double hours, LocalDate start_date, LocalDate end_date, int status, double calculatedTime, int project_id, List<Subtask> subtasks) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.calculatedTime = calculatedTime;
        this.project_id = project_id;
        this.subtasks = subtasks;
    }

    public TaskSubtaskDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getCalculatedTime() {
        return calculatedTime;
    }

    public void setCalculatedTime(double calculatedTime) {
        this.calculatedTime = calculatedTime;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public List<Subtask> getSubtasks(){
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    @Override
    public String toString() {
        return "TaskSubtaskDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hours=" + hours +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", status=" + status +
                ", calculatedTime=" + calculatedTime +
                ", project_id=" + project_id +
                ", subtasks=" + subtasks +
                '}';
    }
}
