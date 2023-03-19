package com.tronina.dashbord.controller;

import com.tronina.dashbord.entity.Issue;
import com.tronina.dashbord.service.FacadeService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping(path = "/release")
public class ProjectController {

//    private final StateMachineService<AssignmentStates, AssignmentEvents, AssignmentEntity> stateMachineService;

    private final FacadeService facadeService;

    //CRUD
    @GetMapping(path = "/tasks/")
    public ResponseEntity<List<Issue>> getAllTasks() {
        List<Issue> documents = facadeService.getTaskService().findAll();//service do mapping
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @GetMapping(path = "/tasks/{id}")
    public ResponseEntity<Issue> getTask(@PathVariable Long id) {
        Issue task = facadeService.getTaskService().findById(id);
        return new ResponseEntity<>(Issue, HttpStatus.OK);
    }

    @PostMapping(path = "/tasks/")
    public ResponseEntity<Issue> createTask(@RequestBody Issue issue) {
            Issue entity = facadeService.getTaskService().saveOrUpdate(issue);
            return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/tasks/{id}")
    public ResponseEntity<HttpStatus> deleteDocument(@PathVariable Long id) {
        try {
            facadeService.getTaskService().deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Document not exists");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*
    //этот список всех событий нужно отдать фронту
    @GetMapping(path = "/assignment/events/")
    public ResponseEntity<?> allEvent() {
        return new ResponseEntity<>(EnumSet.allOf(AssignmentEvents.class), HttpStatus.OK);
    }

    //с фронта принимаем событие и поручение
    @PostMapping(path = "/assignment/events/{Event}")
    public ResponseEntity<String> sendEvent(@PathVariable String event, @RequestBody AssignmentDTO assignmentDTO) {
        try {
            AssignmentEvents nextEvent = stateMachineService.sendEvent(AssignmentEvents.valueOf(event),
                    mapper.toEntity(assignmentDTO));
            return new ResponseEntity<>(nextEvent.name(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
*/
}
