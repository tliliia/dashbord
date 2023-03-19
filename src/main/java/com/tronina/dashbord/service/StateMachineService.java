package com.tronina.dashbord.service;

import org.springframework.statemachine.StateMachine;

public interface StateMachineService<S, E, V> {
    S getState(V object);
    StateMachine<S, E> build(V object);
    E sendEvent(E event, V object);
}
