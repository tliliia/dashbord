package com.tronina.dashbord.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tronina.dashbord.entity.Transitions;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DashboardApplicationTests {

	@Autowired
	private StateMachineFactory<States, Transitions> stateMachineFactory;

	@Autowired
	private StateMachinePersister<States, Transitions, UUID> persister;

	private StateMachine<States, Transitions> stateMachine;


	@BeforeEach
	public void setUp()  {
		stateMachine = stateMachineFactory.getStateMachine();
	}

	@Test
	void initTest() {
		assertThat(stateMachine.getState().getId()).isEqualTo(States.BACKLOG);
		assertThat(stateMachine).isNotNull();
	}

	@Test
	void testGreenFlow() {
		//Arrange and act
		stateMachine.sendEvent(Transitions.START_TASK);
		stateMachine.sendEvent(Transitions.DEPLOY);
		stateMachine.sendEvent(Transitions.FINISH_TASK);
		stateMachine.sendEvent(Transitions.QA_TEAM_APPROVE);

		//assert
		assertThat(stateMachine.getState().getId()).isEqualTo(States.DONE);
	}



	@Test
	void testWrongWay() {
		// Arrange
		// Act
		stateMachine.sendEvent(Transitions.START_TASK);
		stateMachine.sendEvent(Transitions.QA_TEAM_APPROVE);
		// Asserts
		assertThat(stateMachine.getState().getId())
			.isEqualTo(States.IN_PROGRESS);
	}


	@Test
	void testRockStar() {
		// Arrange
		// Act
		stateMachine.sendEvent(Transitions.DEPLOY);
		stateMachine.sendEvent(Transitions.ROCK_STAR_MAKE_ALL_IN_ONE);

		assertThat(stateMachine.getState().getId()).isEqualTo(States.TESTING);
	}

	@Test
	void testGuard() {
		// Arrange and act
		stateMachine.sendEvent(Transitions.START_TASK);
		stateMachine.sendEvent(Transitions.FINISH_TASK);
		stateMachine.sendEvent(Transitions.QA_TEAM_APPROVE);	// not accepted

		assertThat(stateMachine.getState().getId()).isEqualTo(States.IN_PROGRESS);
	}

	@Test
	public void testPersist() throws Exception {
		// Arrange
		StateMachine<States, Transitions> firstStateMachine = stateMachineFactory.getStateMachine();

		StateMachine<States, Transitions> secondStateMachine = stateMachineFactory.getStateMachine();

		firstStateMachine.sendEvent(Transitions.START_TASK);
		firstStateMachine.sendEvent(Transitions.DEPLOY);

		// precondition
		assertThat(secondStateMachine.getState().getId()).isEqualTo(States.BACKLOG);

		// Act
		persister.persist(firstStateMachine, firstStateMachine.getUuid());
		persister.restore(secondStateMachine, firstStateMachine.getUuid());

		// Asserts
		assertThat(secondStateMachine.getState().getId()).isEqualTo(States.IN_PROGRESS);
	}

}
