package com.tronina.dashbord.repository;

import com.tronina.dashbord.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Task, Long> {
    @Override
    List<Task> findAll();
}
