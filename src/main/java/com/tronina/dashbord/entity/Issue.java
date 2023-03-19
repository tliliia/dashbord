package com.tronina.dashbord.entity;

import org.springframework.scheduling.config.Task;
import org.springframework.statemachine.StateMachine;

import java.util.List;

public class Issue {
        private Long id;

        private String title;

        private Employee autor;

        private Employee executor;

        private Relese project;

        private States state;
}