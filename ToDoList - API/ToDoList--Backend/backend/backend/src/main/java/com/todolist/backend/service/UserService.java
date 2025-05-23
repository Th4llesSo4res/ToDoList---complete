package com.todolist.backend.service;

import com.todolist.backend.entity.User;
import com.todolist.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Salva um novo usuário
    public void saveUser(User user) {
        userRepository.save(user);
    }

    // Verifica se e-mail já está em uso
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // Retorna o usuário (ou null, sem lançar exceção)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // Verifica a senha (simples — pode trocar por encoder depois)
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}
