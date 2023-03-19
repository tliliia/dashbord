package com.tronina.dashbord.service.GeneralServiceImpl;

import com.arturishmaev.documentflow.entity.EmployeeEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeGeneralServiceImpl implements GeneralService<Employee>, UserDetailsService {

    private static final String USER_NOT_FOUND_BY_ID_MSG = "User with id = %d not found!";
    private static final String USER_NOT_FOUND_BY_NAME_MSG = "User with email = %s not found!";

    private final EmployeeRepository repository;

    public EmployeeGeneralServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EmployeeEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public EmployeeEntity findByName(String name) {
        return repository.findByEmail(name)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_NAME_MSG, name)));
    }

    @Override
    public EmployeeEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_ID_MSG, id)));
    }

    @Override
    public EmployeeEntity saveOrUpdate(EmployeeEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(EmployeeEntity entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsByName(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_NAME_MSG, username)));
    }
}
