package com.todolist.backend.controller;

import com.todolist.backend.entity.User;
import com.todolist.backend.security.JwtUtil;
import com.todolist.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Cadastro
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userService.emailExists(user.getEmail())) {
            return "E-mail já cadastrado.";
        }

        userService.saveUser(user); // salva o novo usuário
        return "Usuário cadastrado com sucesso!";
    }

    // Login
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User existing = userService.getUserByEmail(user.getEmail());
        if (existing != null && userService.checkPassword(user.getPassword(), existing.getPassword())) {
            String token = jwtUtil.generateToken(existing.getEmail());
            return token; // retorna só o token (frontend lida com o redirecionamento)
        }
        return "Credenciais inválidas.";
    }
}
