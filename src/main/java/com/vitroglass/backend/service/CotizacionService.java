package com.vitroglass.backend.service;

import com.vitroglass.backend.dto.CotizacionRequestDTO;
import com.vitroglass.backend.dto.DetalleRequestDTO;
import com.vitroglass.backend.model.*;
import com.vitroglass.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CotizacionService {

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private DetalleCotizacionRepository detalleCotizacionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoVidrioRepository tipoVidrioRepository;

    public Cotizacion crear(CotizacionRequestDTO dto) {

        Cotizacion cotizacion = new Cotizacion();

        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new RuntimeException());

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException());

        cotizacion.setCliente(cliente);
        cotizacion.setUsuario(usuario);
        cotizacion.setFechaCotizacion(LocalDateTime.now());
        cotizacion.setEstado("PENDIENTE");

        double subtotalTotal = 0;

        Cotizacion savedCotizacion = cotizacionRepository.save(cotizacion);

        for (DetalleRequestDTO d : dto.getDetalles()) {

            TipoVidrio tipoVidrio = tipoVidrioRepository.findById(d.getIdTipoVidrio())
                    .orElseThrow(() -> new RuntimeException());

            double area = d.getAnchoMetros() * d.getAltoMetros();
            double subtotal = area * d.getCantidad() * d.getPrecioUnitario();

            DetalleCotizacion detalle = new DetalleCotizacion();
            detalle.setCotizacion(savedCotizacion);
            detalle.setTipoVidrio(tipoVidrio);
            detalle.setDescripcionProducto(d.getDescripcionProducto());
            detalle.setAnchoMetros(d.getAnchoMetros());
            detalle.setAltoMetros(d.getAltoMetros());
            detalle.setAreaMetrosCuadrados(area);
            detalle.setCantidad(d.getCantidad());
            detalle.setPrecioUnitario(d.getPrecioUnitario());
            detalle.setSubtotal(subtotal);

            subtotalTotal += subtotal;

            detalleCotizacionRepository.save(detalle);
        }

        double impuesto = subtotalTotal * 0.18;
        double total = subtotalTotal + impuesto;

        savedCotizacion.setSubtotal(subtotalTotal);
        savedCotizacion.setImpuesto(impuesto);
        savedCotizacion.setTotal(total);

        return cotizacionRepository.save(savedCotizacion);
    }

    public Cotizacion actualizarEstado(Integer id, String estado) {
        Cotizacion cotizacion = cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        cotizacion.setEstado(estado);
        return cotizacionRepository.save(cotizacion);
    }
}