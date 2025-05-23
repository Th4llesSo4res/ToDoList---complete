package com.todolist.backend.controller;

import com.todolist.backend.entity.Task;
import com.todolist.backend.security.JwtUtil;
import com.todolist.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtUtil jwtUtil;

    // 🔐 Helper para extrair e-mail do token
    private String extractEmailFromToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return jwtUtil.extractEmail(token);
    }

    // ✅ Criar nova tarefa
    @PostMapping
    public String createTask(@RequestHeader("Authorization") String authHeader, @RequestBody Task task) {
        String email = extractEmailFromToken(authHeader);
        taskService.saveTask(task, email);
        return "Tarefa salva com sucesso!";
    }

    // ✅ Buscar tarefas do usuário logado
    @GetMapping
    public List<Task> getAllTasks(@RequestHeader("Authorization") String authHeader) {
        String email = extractEmailFromToken(authHeader);
        return taskService.getTasksByUser(email);
    }

    // ✅ Atualizar tarefa
    @PutMapping("/{id}")
    public Task updateTask(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody Task updatedTask) {
        String email = extractEmailFromToken(authHeader);
        return taskService.updateTask(id, updatedTask, email);
    }

    // ✅ Marcar como concluída
    @PutMapping("/{id}/complete")
    public String completeTask(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        String email = extractEmailFromToken(authHeader);
        Task task = taskService.findById(id);

        if (task != null && task.getUser().getEmail().equals(email)) {
            task.setCompleted(true);
            taskService.saveTask(task, email);
            return "Tarefa marcada como concluída!";
        } else {
            return "Tarefa não encontrada ou você não tem permissão.";
        }
    }

    // ✅ Deletar tarefa
    @DeleteMapping("/{id}")
    public String deleteTask(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        String email = extractEmailFromToken(authHeader);
        taskService.deleteTask(id, email);
        return "Tarefa deletada com sucesso!";
    }
}
