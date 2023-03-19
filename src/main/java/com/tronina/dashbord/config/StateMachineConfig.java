package com.tronina.dashbord.config;

import com.tronina.dashbord.entity.States;
import com.tronina.dashbord.entity.Transitions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

@Slf4j
@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Transitions> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Transitions> config) throws Exception {
        config.withConfiguration()
                .listener(listener())
                .autoStartup(true);
    }

    private StateMachineListener<States, Transitions> listener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void eventNotAccepted(Message<Transitions> event) {
                log.error("Not accepted event: {}", event);
//                throw new TransitionNotAllowedException("Проект еще не стартовал");
            }

            @Override
            public void transition(org.springframework.statemachine.transition.Transition transition) {
                log.warn("CHANGE STATE {} --> {}",
                        String.valueOf(transition.getSource()), String.valueOf(transition.getTarget()));
            }
        };
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Transitions> states) throws Exception {
        states.withStates()
                .initial(States.BACKLOG)
                .state(States.IN_PROGRESS)
                .end(States.DONE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Transitions> transition) throws Exception {
        transition.withExternal()
                .source(States.BACKLOG)
                .target(States.IN_PROGRESS)
                .event(Transitions.START_TASK)
                .guard(checkProjectHasStarted())

                .and()
                .withExternal()
                .source(States.IN_PROGRESS)
                .target(States.DONE)
                .event(Transitions.FINISH_TASK)
        ;

    }

    private Guard<States, Transitions> checkProjectHasStarted() {
        return stateContext -> {
            Boolean flag = (Boolean) stateContext.getExtendedState()
                    .getVariables()
                    .get("deployed");
            return flag == null ? false : flag;
        };
    }

    private Action<States, Transitions> deployAction() {
        return stateContext -> {
            log.warn("DEPLOYING: {}", stateContext.getEvent());
            stateContext.getExtendedState()
                    .getVariables()
                    .put("deployed", true);
        };
    }


}
