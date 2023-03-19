package com.tronina.dashbord.service.GeneralServiceImpl;

import com.tronina.dashbord.entity.Release;
import com.tronina.dashbord.repository.ReleaseRepository;
import com.tronina.dashbord.service.AbstractCrudService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Сервис Управление проектами (создание, редактирование)
 */
@Service
@NoArgsConstructor
@AllArgsConstructor
public class ProjectGeneralServiceImpl implements AbstractCrudService<Release> {

    private final ReleaseRepository repository;

}
