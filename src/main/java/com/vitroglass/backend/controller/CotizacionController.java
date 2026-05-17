package com.vitroglass.backend.controller;

import com.vitroglass.backend.dto.CotizacionRequestDTO;
import com.vitroglass.backend.model.Cotizacion;
import com.vitroglass.backend.service.CotizacionService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping
    public List<Cotizacion> listar() {
        return cotizacionService.listarCotizaciones();
    }

    @GetMapping("/{id}")
    public Cotizacion obtenerPorId(@PathVariable Integer id) {
        return cotizacionService.obtenerPorId(id);
    }

    @GetMapping("/estado/{estado}")
    public List<Cotizacion> listarPorEstado(@PathVariable String estado) {
        return cotizacionService.listarPorEstado(estado);
    }
    
    @PutMapping("/{id}/estado")
public Cotizacion actualizarEstado(
        @PathVariable Integer id,
        @RequestBody Map<String, Object> body
) {
    String estado = (String) body.get("estado");

    LocalDateTime fechaEntrega = null;

    if (body.get("fechaEntrega") != null) {
        fechaEntrega = LocalDateTime.parse(body.get("fechaEntrega").toString());
    }

    return cotizacionService.actualizarEstado(id, estado, fechaEntrega);
}
}