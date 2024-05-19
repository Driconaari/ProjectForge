package com.example.ProjectForge.repository;

import com.example.ProjectForge.model.Project;
import com.example.ProjectForge.model.Task;

import java.util.List;

public interface IProjectRepository {

    //Get projects by organization_id
    public List<Project> getProjectsByID(int organization_id);

    //Create project by user_id
    public void createProject(Project project, int user_id);

    //Edit project by project_id and user_id
    public void editProject(Project project, int project_id, int user_id);

    //Get project by project_id and user_id
    public Project getProjectByIDs(int project_id, int user_id);

    //Get project by project_id
    public Project getProjectByProjectID(int project_id);

    //Delete  project by project_id
    public void deleteProject(int projectId);

    //Get project_id by task_id
    public int getProjectID(int task_id);

    //Get calculated time for project
    Double getProjectTimeByProjectID(int project_id);

    //Get all projects
    List<Project> getAllProjects();

    List<Task> getTasksWithSubtasksByProjectID(int project_id);
}
