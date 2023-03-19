package com.tronina.dashbord.repository;

import com.tronina.dashbord.entity.Release;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReleaseRepository extends JpaRepository<Release, Long> {
    @Override
    List findAll();
}
