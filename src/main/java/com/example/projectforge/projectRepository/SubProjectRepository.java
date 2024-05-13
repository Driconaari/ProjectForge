package com.example.projectforge.projectRepository;

import com.example.projectforge.model.SubProject;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubProjectRepository {

    SubProject findBySubProjectName(String subProjectName) throws SQLException;

    SubProject save(SubProject subproject) throws SQLException;

    Optional<SubProject> findById(int id);

    List<SubProject> findAll();

    void deleteById(int id);


}