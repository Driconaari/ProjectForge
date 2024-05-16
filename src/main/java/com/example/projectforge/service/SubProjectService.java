
package com.example.projectforge.service;

import com.example.projectforge.model.SubProject;
import com.example.projectforge.projectRepository.SubProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class SubProjectService {

    private final SubProjectRepository subProjectRepository;

    @Autowired
    public SubProjectService(SubProjectRepository subProjectRepository) {
        this.subProjectRepository = subProjectRepository;
    }

    public SubProject createAndSaveSubProject() throws SQLException {
        SubProject subProject = new SubProject();
        // set properties of subProject
        return subProjectRepository.save(subProject);
    }


    public void printSubProjectNames(int projectId) {
        List<SubProject> subProjects = subProjectRepository.findByParentProjectId(projectId);
        for (SubProject subProject : subProjects) {
            System.out.println(subProject.getSubProjectName());
        }

    }
}
