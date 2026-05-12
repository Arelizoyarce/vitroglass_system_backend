package com.vitroglass.backend.controller;

import com.vitroglass.backend.dto.LoginRequest;
import com.vitroglass.backend.dto.LoginResponse;
import com.vitroglass.backend.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        return usuarioService.login(
                request.getCorreoElectronico(),
                request.getContrasena()
        );

    }

}