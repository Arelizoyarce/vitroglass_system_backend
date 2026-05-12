package com.vitroglass.backend.service;

import com.vitroglass.backend.model.TipoVidrio;
import com.vitroglass.backend.repository.TipoVidrioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoVidrioService {

    @Autowired
    private TipoVidrioRepository tipoVidrioRepository;

    public List<TipoVidrio> listarActivos() {
        return tipoVidrioRepository.findByEstado("ACTIVO");
    }
}