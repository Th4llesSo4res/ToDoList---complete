package com.todolist.backend.service;

import com.todolist.backend.entity.Task;
import com.todolist.backend.entity.User;
import com.todolist.backend.repository.TaskRepository;
import com.todolist.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Criar tarefa associada ao usuário
    public void saveTask(Task task, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o e-mail: " + email));
        task.setUser(user);
        taskRepository.save(task);
    }

    // ✅ Buscar tarefas do usuário
    public List<Task> getTasksByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o e-mail: " + email));
        return taskRepository.findByUser(user);
    }

    // ✅ Atualizar tarefa (com verificação de dono)
    public Task updateTask(Long id, Task updatedTask, String email) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com id: " + id));

        if (!existingTask.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Você não tem permissão para atualizar esta tarefa.");
        }

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.isCompleted());

        return taskRepository.save(existingTask);
    }

    // ✅ Encontrar por ID
    public Task findById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    // ✅ Deletar (verificando se é do usuário)
    public void deleteTask(Long id, String email) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        if (!task.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Você não tem permissão para excluir esta tarefa.");
        }

        taskRepository.deleteById(id);
    }
}
