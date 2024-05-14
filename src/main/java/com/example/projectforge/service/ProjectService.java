package com.example.projectforge.service;

import com.example.projectforge.model.Project;
import com.example.projectforge.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    //delete project
    public void deleteProject(int project_id) {
       projectRepository.deleteProject(project_id);
    }


    //Get projects by project_id
    public List<Project> getProjectsByID(long user_id) {
        return projectRepository.getProjectsByID(user_id);
    }

    //Create project
    public void createProject(Project project, long user_id) {
        projectRepository.createProject(project, user_id);
    }

    //Edit project
    public void editProject(Project project, int project_id, int user_id) {
        projectRepository.editProject(project, project_id, user_id);
    }

    //Get project by IDs
    public Project getProjectByIDs(int project_id, int user_id) {
        return projectRepository.getProjectByIDs(project_id, user_id);
    }

    //Get project by project_id
    public Project getProjectByProjectID(int project_id) {
        return projectRepository.getProjectByProjectID(project_id);
    }

    //Get project_id from task_id
    public int getProjectID (int task_id){
        return projectRepository.getProjectID(task_id);
    }

    //Projects calculated estimated time
    public Double getProjectTimeByProjectID (int project_id) {
        return projectRepository.getProjectTimeByProjectID(project_id);
    }


}
