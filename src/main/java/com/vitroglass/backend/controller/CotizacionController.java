package com.vitroglass.backend.controller;

import com.vitroglass.backend.dto.CotizacionRequestDTO;
import com.vitroglass.backend.model.Cotizacion;
import com.vitroglass.backend.service.CotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cotizaciones")
@CrossOrigin("*")
public class CotizacionController {

    @Autowired
    private CotizacionService cotizacionService;

    @PostMapping
    public Cotizacion crear(@RequestBody CotizacionRequestDTO dto) {
        return cotizacionService.crear(dto);
    }

    @PutMapping("/{id}")
    public Cotizacion actualizarEstado(@PathVariable Integer id, @RequestBody Cotizacion cotizacion) {
        return cotizacionService.actualizarEstado(id, cotizacion.getEstado());
    }
}