package com.mycompany.restaurante.dto.response;

import com.mycompany.restaurante.model.Mesero;

import java.util.Date;

public class MeseroResponseDTO {

    private int id;
    private String nombre;
    private String cedula;
    private String telefono;
    private double salario;
    private Date fechaVinculacion;

    // NUEVOS CAMPOS
    private String turno;
    private String nivel;
    private String areaAsignada;

    public MeseroResponseDTO() {}

    public static MeseroResponseDTO from(Mesero mesero) {

        MeseroResponseDTO dto = new MeseroResponseDTO();

        dto.setId(mesero.getId());
        dto.setNombre(mesero.getNombre());
        dto.setCedula(mesero.getCedula());
        dto.setTelefono(mesero.getTelefono());
        dto.setSalario(mesero.getSalario());
        dto.setFechaVinculacion(mesero.getFechaVinculacion());

        // NUEVOS MAPEOS
        dto.setTurno(mesero.getTurno());
        dto.setNivel(mesero.getNivel());
        dto.setAreaAsignada(mesero.getAreaAsignada());

        return dto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Date getFechaVinculacion() {
        return fechaVinculacion;
    }

    public void setFechaVinculacion(Date fechaVinculacion) {
        this.fechaVinculacion = fechaVinculacion;
    }

    // GETTERS Y SETTERS NUEVOS

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getAreaAsignada() {
        return areaAsignada;
    }

    public void setAreaAsignada(String areaAsignada) {
        this.areaAsignada = areaAsignada;
    }
}