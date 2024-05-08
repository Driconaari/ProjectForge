package com.example.projectforge.ResourceRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.projectforge.model.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
}