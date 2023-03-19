package com.tronina.dashbord.entity;

import org.springframework.scheduling.config.Task;

import java.util.List;

public class Employee {
        private Long id;

        private String login;

        private List<Task> createdTasks;

        private List<Task> completedTask;
}