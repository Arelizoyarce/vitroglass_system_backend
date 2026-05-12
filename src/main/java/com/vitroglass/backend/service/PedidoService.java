package com.vitroglass.backend.service;

import com.vitroglass.backend.model.Pedido;
import com.vitroglass.backend.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido obtenerPorId(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    public List<Pedido> listarPorEstado(String estado) {
        return pedidoRepository.findByEstadoPedidoNombreEstado(estado);
    }
}