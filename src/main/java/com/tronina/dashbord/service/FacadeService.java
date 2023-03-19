package com.tronina.dashbord.service;

import com.tronina.dashbord.entity.Employee;
import com.tronina.dashbord.entity.Release;
import com.tronina.dashbord.entity.Task;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Getter
@Service
public class FacadeService {

    private final AbstractCrudService<Employee> employeeService;
    private final AbstractCrudService<Release> projectService;
    private final AbstractCrudService<Task> taskService;
}
