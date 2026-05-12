package com.vitroglass.backend.controller;

import com.vitroglass.backend.model.TipoVidrio;
import com.vitroglass.backend.service.TipoVidrioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-vidrio")
@CrossOrigin("*")
public class TipoVidrioController {

    @Autowired
    private TipoVidrioService tipoVidrioService;

    @GetMapping
    public List<TipoVidrio> listar() {
        return tipoVidrioService.listarActivos();
    }
}