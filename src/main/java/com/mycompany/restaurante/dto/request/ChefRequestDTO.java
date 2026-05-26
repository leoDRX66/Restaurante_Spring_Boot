package com.mycompany.restaurante.dto.request;

import com.mycompany.restaurante.model.Chef;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.sql.Time;
import java.util.Date;

public class ChefRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La cédula es obligatoria")
    private String cedula;

    private String telefono;

    @Positive(message = "El salario debe ser mayor a 0")
    private double salario;

    private Date fechaVinculacion;
    private String horaIngreso;  // formato "HH:mm:ss"
    private String horaSalida;   // formato "HH:mm:ss"

    public ChefRequestDTO() {}

    public Chef toEntity() {
        Chef chef = new Chef();
        chef.setNombre(nombre);
        chef.setCedula(cedula);
        chef.setTelefono(telefono);
        chef.setSalario(salario);
        chef.setFechaVinculacion(fechaVinculacion != null ? fechaVinculacion : new Date());
        chef.setHoraIngreso(horaIngreso != null ? Time.valueOf(horaIngreso) : null);
        chef.setHoraSalida(horaSalida != null ? Time.valueOf(horaSalida) : null);
        return chef;
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
