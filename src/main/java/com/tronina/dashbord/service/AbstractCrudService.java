package com.tronina.dashbord.service;

import java.util.List;
import java.util.UUID;

public interface AbstractCrudService<T> {
    T saveOrUpdate(T entity);
    void deleteById(Long id);
    void delete(T entity);
    List<T> findAll();
    T findById(Long id);
}
