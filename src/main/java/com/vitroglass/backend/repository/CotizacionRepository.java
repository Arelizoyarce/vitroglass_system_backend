package com.vitroglass.backend.repository;

import com.vitroglass.backend.model.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer> {

    List<Cotizacion> findByEstado(String estado);

}