package com.tronina.dashbord.controller;

import com.arturishmaev.documentflow.dto.AssignmentDTO;
import com.arturishmaev.documentflow.dto.DocumentDTO;
import com.arturishmaev.documentflow.dto.EmployeeDTO;
import com.arturishmaev.documentflow.dto.Mapper;
import com.arturishmaev.documentflow.entity.*;
import com.arturishmaev.documentflow.service.GeneralService;
import com.arturishmaev.documentflow.service.StateMachineService;
import com.arturishmaev.documentflow.statemachine.AssignmentEvents;
import com.arturishmaev.documentflow.statemachine.AssignmentStates;
import com.tronina.dashbord.entity.Employee;
import com.tronina.dashbord.entity.Release;
import com.tronina.dashbord.entity.Task;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/project")
public class ProjectController {

    private final StateMachineService<AssignmentStates, AssignmentEvents, AssignmentEntity> stateMachineService;

    private final GeneralService<Release> projectService;
    private final GeneralService<Task> departmentGeneralService;
    private final GeneralService<Employee> organizationGeneralService;

    private final Mapper mapper;

    //CRUD Document
    @GetMapping(path = "/document/")
    public ResponseEntity<List<DocumentDTO>> getAllDocument() {
        List<DocumentDTO> documents = documentGeneralService.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
        return !documents.isEmpty() ? new ResponseEntity<>(documents, HttpStatus.OK)
                                    : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/document/{id}")
    public ResponseEntity<DocumentDTO> getDocument(@PathVariable Long id) {
        DocumentEntity document = documentGeneralService.findById(id);
        return (document != null) ? new ResponseEntity<>(mapper.toDto(document), HttpStatus.OK)
                                  : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/document/")
    public ResponseEntity<DocumentDTO> createDocument(@RequestBody DocumentEntity document) {
        try {
            DocumentEntity documentEntity = documentGeneralService.saveOrUpdate(document);
            return new ResponseEntity<>(mapper.toDto(documentEntity), HttpStatus.CREATED);
        } catch (Exception e) {
            log.warn("Document creation error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/document/{id}")
    public ResponseEntity<DocumentDTO> updateDocument(@PathVariable Long id, @RequestBody DocumentEntity document) {
        DocumentEntity documentEntity = documentGeneralService.findById(id);
        if (documentEntity != null) {
            documentEntity.fromDocumentEntity(document);
            documentGeneralService.saveOrUpdate(documentEntity);
            return new ResponseEntity<>(mapper.toDto(documentEntity), HttpStatus.CREATED);
        } else {
            log.warn("Document update error");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/document/{id}")
    public ResponseEntity<HttpStatus> deleteDocument(@PathVariable Long id) {
        try {
            documentGeneralService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Document not exists");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //CRUD Assignment
    @GetMapping(path = "/assignment/")
    public ResponseEntity<List<AssignmentDTO>> getAllAssignment() {
        List<AssignmentDTO> assignments = assignmentGeneralService.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
        return assignments.isEmpty() ? new ResponseEntity<>(assignments, HttpStatus.OK)
                                     : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/assignment/{id}")
    public ResponseEntity<AssignmentDTO> getAssignment(@PathVariable Long id) {
        AssignmentEntity assignment = assignmentGeneralService.findById(id);
        return (assignment != null) ? new ResponseEntity<>(mapper.toDto(assignment), HttpStatus.OK)
                                    : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/assignment/")
    public ResponseEntity<AssignmentDTO> createAssignment(@RequestBody AssignmentEntity assignment) {
        try {
            AssignmentEntity assignmentEntity = assignmentGeneralService.saveOrUpdate(assignment);
            return new ResponseEntity<>(mapper.toDto(assignmentEntity), HttpStatus.CREATED);
        } catch (Exception e) {
            log.warn("Assignment creation error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/assignment/{id}")
    public ResponseEntity<AssignmentDTO> updateAssignment(@PathVariable Long id, @RequestBody AssignmentEntity assignment) {
        AssignmentEntity assignmentEntity = assignmentGeneralService.findById(id);
        if (assignmentEntity != null) {
            assignmentEntity.fromAssignment(assignment);
            assignmentGeneralService.saveOrUpdate(assignmentEntity);
            return new ResponseEntity<>(mapper.toDto(assignmentEntity), HttpStatus.CREATED);
        } else {
            log.warn("Assignment update error");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/assignment/{id}")
    public ResponseEntity<HttpStatus> deleteAssignment(@PathVariable Long id) {
        try {
            assignmentGeneralService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Assignment not exists");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //с фронта принимаем событие и поручение
    @PostMapping(path = "/assignment/events/{Event}")
    public ResponseEntity<String> sendEvent(@PathVariable String event, @RequestBody AssignmentDTO assignmentDTO) {
        try {
            AssignmentEvents nextEvent = stateMachineService.sendEvent(AssignmentEvents.valueOf(event), mapper.toEntity(assignmentDTO));
            return new ResponseEntity<>(nextEvent.name(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //этот список всех событий нужно отдать фронту
    @GetMapping(path = "/assignment/events/")
    public ResponseEntity<?> allEvent() {
        return new ResponseEntity<>(EnumSet.allOf(AssignmentEvents.class), HttpStatus.OK);
    }

    @GetMapping(path = "/employee/")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee() {
        List<EmployeeDTO> employees = employeeGeneralService
                .findAll()
                .stream()
                .map(employee -> mapper.toDto(employee))
                .collect(Collectors.toList());
        return !employees.isEmpty() ? new ResponseEntity<>(employees, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/employee/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        EmployeeEntity employeeEntity = employeeGeneralService.findById(id);
        return employeeEntity != null ? new ResponseEntity<>(mapper.toDto(employeeEntity), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
