package com.tronina.dashbord.config;

import com.tronina.dashbord.service.StateMachineServiceImpl.DBStateMachinePersist;
import com.tronina.dashbord.statemachine.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.UUID;

@Configuration
public class PersistConfig {

    @Bean
    @Profile({"in-memory", "default"})
    public StateMachinePersist<States, Transitions, UUID> inMemoryPersist() {
        return new DBStateMachinePersist();
    }

    @Bean
    public StateMachinePersister<States, Transitions, UUID> persister(StateMachinePersist<States, Transitions, UUID> defaultPersist) {
        return new DefaultStateMachinePersister<>(defaultPersist);
    }
}
