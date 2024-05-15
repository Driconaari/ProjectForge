package com.example.projectforge.repository;

import com.example.projectforge.model.Project;

import java.util.List;

public interface IProjectRepository {

    //Get projects from org
    public List<Project> getProjectsByID(long user_id);

    //Create project
    public void createProject(Project project, long user_id);

    //Edit project
    public void editProject(Project project, int project_id, int user_id);

    //Get project by project_id and user_id
    public Project getProjectByIDs(int project_id, int user_id);

    //Get project by project_id
    public Project getProjectByProjectID(int project_id);

    //Delete project
    public void deleteProject(int projectId);

    //Get project by task_id
    public int getProjectID(int task_id);

    //Get time for all tasks and subtasks
    Double getProjectTimeByProjectID(int project_id);
}
