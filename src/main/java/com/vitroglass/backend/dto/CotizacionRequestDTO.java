/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vitroglass.backend.dto;

/**
 *
 * @author Areliz
 */

import java.util.List;

public class CotizacionRequestDTO {

    private Integer idCliente;
    private Integer idUsuario;
    private List<DetalleRequestDTO> detalles;

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<DetalleRequestDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleRequestDTO> detalles) {
        this.detalles = detalles;
    }
}
