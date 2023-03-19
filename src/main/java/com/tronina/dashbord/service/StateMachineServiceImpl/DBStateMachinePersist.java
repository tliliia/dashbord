package com.tronina.dashbord.service.StateMachineServiceImpl;

import java.util.HashMap;
import java.util.UUID;

import com.tronina.dashbord.entity.Transitions;
import com.tronina.dashbord.entity.States;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;

public class DBStateMachinePersist implements StateMachinePersist<States, Transitions, UUID> {

    private HashMap<UUID, StateMachineContext<States, Transitions>> storage = new HashMap<>();

    @Override
    public void write(final StateMachineContext<States, Transitions> context, final UUID contextObj) throws Exception {
        storage.put(contextObj, context);
    }

    @Override
    public StateMachineContext<States, Transitions> read(final UUID contextObj) throws Exception {
        return storage.get(contextObj);
    }
}
