package com.vitroglass.backend.service;

import com.vitroglass.backend.dto.LoginResponse;
import com.vitroglass.backend.model.Usuario;
import com.vitroglass.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }
    
    public LoginResponse login(String correo, String contrasena) {

    Optional<Usuario> usuarioOptional =
            usuarioRepository.findByCorreoElectronico(correo);

    if (usuarioOptional.isEmpty()) {
        throw new RuntimeException("Usuario no encontrado");
    }

    Usuario usuario = usuarioOptional.get();

    if (!usuario.getContrasena().equals(contrasena)) {
        throw new RuntimeException("Contraseña incorrecta");
    }

    return new LoginResponse(
            usuario.getIdUsuario(),
            usuario.getNombres(),
            usuario.getApellidos(),
            usuario.getCorreoElectronico(),
            usuario.getRol(),
            "Login exitoso"
    );

}

}