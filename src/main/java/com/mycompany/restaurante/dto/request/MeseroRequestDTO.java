package com.mycompany.restaurante.dto.request;

import com.mycompany.restaurante.model.Mesero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.sql.Time;
import java.util.Date;

public class MeseroRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La cédula es obligatoria")
    private String cedula;

    private String telefono;

    @Positive(message = "El salario debe ser mayor a 0")
    private double salario;

    private Date fechaVinculacion;
    private String horaIngreso;
    private String horaSalida;

    public MeseroRequestDTO() {}

    public Mesero toEntity() {
        Mesero mesero = new Mesero();
        mesero.setNombre(nombre);
        mesero.setCedula(cedula);
        mesero.setTelefono(telefono);
        mesero.setSalario(salario);
        mesero.setFechaVinculacion(fechaVinculacion != null ? fechaVinculacion : new Date());
        mesero.setHoraIngreso(horaIngreso != null ? Time.valueOf(horaIngreso) : null);
        mesero.setHoraSalida(horaSalida != null ? Time.valueOf(horaSalida) : null);
        return mesero;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    public Date getFechaVinculacion() { return fechaVinculacion; }
    public void setFechaVinculacion(Date fechaVinculacion) { this.fechaVinculacion = fechaVinculacion; }

    public String getHoraIngreso() { return horaIngreso; }
    public void setHoraIngreso(String horaIngreso) { this.horaIngreso = horaIngreso; }

    public String getHoraSalida() { return horaSalida; }
    public void setHoraSalida(String horaSalida) { this.horaSalida = horaSalida; }
}
