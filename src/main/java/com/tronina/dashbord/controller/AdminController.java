package com.tronina.dashbord.controller;

import com.arturishmaev.documentflow.dto.*;
import com.arturishmaev.documentflow.entity.DepartmentEntity;
import com.arturishmaev.documentflow.entity.EmployeeEntity;
import com.arturishmaev.documentflow.entity.OrganizationEntity;
import com.arturishmaev.documentflow.entity.RoleEntity;
import com.arturishmaev.documentflow.service.GeneralService;
import com.tronina.dashbord.service.FacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
//@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "/api/admin")
public class AdminController {

    private final FacadeService service;

    private final PasswordEncoder passwordEncoder;

    private final Mapper mapper;


    //CRUD Employee
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

    @PostMapping(path = "/employee/")
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeEntity employeeEntity) {
        try {
            String password = employeeEntity.getPassword();
            employeeEntity.setPassword(passwordEncoder.encode(password));
            EmployeeEntity employeeSaveEntity = employeeGeneralService.saveOrUpdate(employeeEntity);
            return new ResponseEntity<>(mapper.toDto(employeeSaveEntity), HttpStatus.CREATED);
        } catch (Exception e) {
            log.warn("Employee creation error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/employee/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long id) {
        try {
            employeeGeneralService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Employee update error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/employee/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employee) {
        try {
            EmployeeEntity employeeEntity = mapper.toEntity(employee);
            employeeGeneralService.saveOrUpdate(employeeEntity);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Employee not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //CRUD Organization
    @GetMapping(path = "/organization/")
    public ResponseEntity<List<OrganizationDTO>> getAllOrganization() {
        List<OrganizationDTO> organizations = organizationGeneralService.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
        organizations.forEach(System.out::println);
        return !organizations.isEmpty() ? new ResponseEntity<>(organizations, HttpStatus.OK)
                                       : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/organization/{id}")
    public ResponseEntity<OrganizationDTO> getOrganization(@PathVariable Long id) {
        OrganizationEntity organizationEntity = organizationGeneralService.findById(id);
        return organizationEntity != null ? new ResponseEntity<>(mapper.toDto(organizationEntity), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/organization/")
    public ResponseEntity<OrganizationDTO> createOrganization(@Valid @RequestBody OrganizationEntity organization) {
        try {
            OrganizationEntity organizationEntity = organizationGeneralService.saveOrUpdate(organization);
            return new ResponseEntity<>(mapper.toDto(organizationEntity), HttpStatus.CREATED);
        } catch (Exception e) {
            log.warn("Organization creation error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/organization/{id}")
    public ResponseEntity<HttpStatus> deleteOrganization(@PathVariable Long id) {
        try {
            organizationGeneralService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Organization update error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/organization/{id}")
    public ResponseEntity<OrganizationEntity> updateOrganization(@PathVariable Long id
            , @Valid @RequestBody OrganizationEntity organization) {
        OrganizationEntity organizationEntity = organizationGeneralService.findById(id);
        try {
            organizationEntity.fromOrganization(organization);
            organizationGeneralService.saveOrUpdate(organizationEntity);
            return new ResponseEntity<>(organizationEntity, HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Organization  not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //CRUD Department
    @GetMapping(path = "/department/")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment() {
        List<DepartmentEntity> departmentEntities = departmentGeneralService.findAll();
        List<DepartmentDTO> departments = departmentEntities.stream().map(mapper::toDto).collect(Collectors.toList());
        return !departments.isEmpty() ? new ResponseEntity<>(departments, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/department/{id}")
    public ResponseEntity<DepartmentEntity> getDepartment(@PathVariable Long id) {
        DepartmentEntity departmentEntity = departmentGeneralService.findById(id);
        return departmentEntity != null ? new ResponseEntity<>(departmentEntity, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/department/")
    public ResponseEntity<DepartmentEntity> createDepartment(@Valid @RequestBody DepartmentEntity department) {
        try {
            DepartmentEntity departmentEntity = departmentGeneralService.saveOrUpdate(department);
            return new ResponseEntity<>(departmentEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            log.warn("Department creation error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/department/{id}")
    public ResponseEntity<HttpStatus> deleteDepartment(@PathVariable Long id) {
        try {
            departmentGeneralService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Department update error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/department/{id}")
    public ResponseEntity<DepartmentEntity> updateDepartment(@PathVariable Long id
            , @Valid @RequestBody DepartmentEntity department) {

        DepartmentEntity departmentEntity = departmentGeneralService.findById(id);
        try {
            departmentEntity.fromDepartment(department);
            departmentGeneralService.saveOrUpdate(departmentEntity);
            return new ResponseEntity<>(departmentEntity, HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Department not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/role/")
    public ResponseEntity<List<RoleDTO>> getAllRole() {
        List<RoleEntity> roleEntities = roleService.findAll();
        List<RoleDTO> roles = roleEntities.stream().map(mapper::toDto).collect(Collectors.toList());
        return !roles.isEmpty() ? new ResponseEntity<>(roles, HttpStatus.OK)
                                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
