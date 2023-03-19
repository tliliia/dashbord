package com.tronina.dashbord.service.GeneralServiceImpl;

import com.tronina.dashbord.entity.Task;
import com.tronina.dashbord.repository.IssueRepository;
import com.tronina.dashbord.service.AbstractCrudService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Сервис Управление задачами (создание, редактирование, удаление)
 * Процесс выполнения задач (смена статуса задачи) и завершение
 */
@Service
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class TaskGeneralServiceImpl implements AbstractCrudService<Task> {

    private final IssueRepository repository;

}
