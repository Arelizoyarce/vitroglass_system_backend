package com.vitroglass.backend.controller;

import com.vitroglass.backend.dto.DashboardItemDTO;
import com.vitroglass.backend.model.Pedido;
import com.vitroglass.backend.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin("*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.listarPedidos();
    }

    @GetMapping("/{id}")
    public Pedido obtenerPedido(@PathVariable Integer id) {
        return pedidoService.obtenerPorId(id);
    }

    @GetMapping("/estado/{estado}")
    public List<Pedido> listarPorEstado(@PathVariable String estado) {
        return pedidoService.listarPorEstado(estado);
    }

    @GetMapping("/dashboard")
    public List<DashboardItemDTO> dashboard() {
        return pedidoService.listarDashboard();
    }
}