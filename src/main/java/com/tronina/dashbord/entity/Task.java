`package com.tronina.dashbord.entity;

import com.tronina.dashbord.statemachine.States;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.statemachine.StateMachine;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "author")
    private Employee autor;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "executor")
    private Employee executor;

    private Project project;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private States state;

    @Transient
    private StateMachine<AssignmentStates, AssignmentEvents> stateMachine;
    //mapper.ToDTO.setStateMachine(assignmentEntity.getStateMachine());

}
`