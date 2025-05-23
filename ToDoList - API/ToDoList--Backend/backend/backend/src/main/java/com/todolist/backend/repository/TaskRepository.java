package com.todolist.backend.repository;

import com.todolist.backend.entity.Task;
import com.todolist.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user); // ✅ necessário para buscar tarefas do usuário logado
}
