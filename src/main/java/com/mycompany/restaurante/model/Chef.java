package com.mycompany.restaurante.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("CHEF")
public class Chef extends Empleado {

    private double salario;

    // NUEVOS ATRIBUTOS
    private String turno;
    private String especialidad;
    private int anosExperiencia;

    // RELACIÓN CON PEDIDOS (Ignorada en JSON para evitar el error 500 de bucle infinito)
    @JsonIgnore
    @OneToMany(mappedBy = "chef")
    private List<Pedido> pedidos;

    // Constructor vacío obligatorio para JPA
    public Chef() {}

    // 1. CONSTRUCTOR ANTIGUO (Mantenlo para que no se rompa el resto de tu proyecto)
    public Chef(double salario, Date fechaVinculacion, Time horaIngreso, Time horaSalida,
                String nombre, String cedula, String telefono) {
        super(fechaVinculacion, horaIngreso, horaSalida, nombre, cedula, telefono);
        this.salario = salario;
    }

    // 2. NUEVO CONSTRUCTOR (El que usa nuestro DataInitializer)
    public Chef(String nombre, String turno, String especialidad, int anosExperiencia, double salario) {
        super(); // Llama al constructor vacío de Empleado
        this.setNombre(nombre); // Asigna el nombre usando el método heredado de Persona
        this.turno = turno;
        this.especialidad = especialidad;
        this.anosExperiencia = anosExperiencia;
        this.salario = salario;
    }

    // Getters y Setters antiguos
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    // NUEVOS Getters y Setters
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public int getAnosExperiencia() { return anosExperiencia; }
    public void setAnosExperiencia(int anosExperiencia) { this.anosExperiencia = anosExperiencia; }

    // Getters y Setters de la lista de Pedidos
    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }

    @Override
    public String toString() { return getNombre(); }
}