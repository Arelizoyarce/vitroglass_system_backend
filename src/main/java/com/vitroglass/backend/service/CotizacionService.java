package com.vitroglass.backend.service;

import com.vitroglass.backend.dto.CotizacionRequestDTO;
import com.vitroglass.backend.dto.DetalleRequestDTO;
import com.vitroglass.backend.model.*;
import com.vitroglass.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

    /* =========================
        CREAR COTIZACION
    ========================= */
    @Transactional
    public Cotizacion crear(CotizacionRequestDTO dto) {

        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setCliente(cliente);
        cotizacion.setUsuario(usuario);
        cotizacion.setFechaCotizacion(LocalDateTime.now());
        cotizacion.setEstado(dto.getEstado() != null ? dto.getEstado() : "COTIZADO");

        Cotizacion savedCotizacion = cotizacionRepository.save(cotizacion);

        double subtotalTotal = 0;

        for (DetalleRequestDTO d : dto.getDetalles()) {

            TipoVidrio tipoVidrio = tipoVidrioRepository.findById(d.getIdTipoVidrio())
                    .orElseThrow(() -> new RuntimeException("Tipo vidrio no encontrado"));

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

    /* =========================
        OBTENER POR ID (IMPORTANTE PARA FRONT)
    ========================= */
    public Cotizacion obtenerPorId(Integer id) {

        Cotizacion cotizacion = cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        // fuerza carga de detalles (evita lazy loading incompleto)
        cotizacion.getDetalleCotizaciones().size();

        return cotizacion;
    }

    /* =========================
        LISTAR TODAS
    ========================= */
    public List<Cotizacion> listarCotizaciones() {
        List<Cotizacion> list = cotizacionRepository.findAllByOrderByFechaCotizacionDesc();

        // opcional: forzar carga de detalles
        list.forEach(c -> {
            if (c.getDetalleCotizaciones() != null) {
                c.getDetalleCotizaciones().size();
            }
        });

        return list;
    }

    /* =========================
        POR ESTADO
    ========================= */
    public List<Cotizacion> listarPorEstado(String estado) {
        return cotizacionRepository.findByEstado(estado);
    }

    /* =========================
        ACTUALIZAR ESTADO
    ========================= */
    public Cotizacion actualizarEstado(Integer id, String estado) {

        Cotizacion cotizacion = cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        cotizacion.setEstado(estado);

        return cotizacionRepository.save(cotizacion);
    }
}