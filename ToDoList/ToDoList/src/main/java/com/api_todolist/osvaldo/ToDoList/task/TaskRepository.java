package com.api_todolist.osvaldo.ToDoList.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByIdUser(UUID idUser);

    //TaskModel findByIdandIdUser(UUID id, UUID idUser);

}
