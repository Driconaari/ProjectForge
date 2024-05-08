package com.example.projectforge.ResourceRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.projectforge.model.ResourceAllocation;

@Repository
public interface ResourceAllocationRepository extends JpaRepository<ResourceAllocation, Long> {
}