package com.mycompany.restaurante.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("MESERO")
public class Mesero extends Empleado {

    private double salario;

    // NUEVOS ATRIBUTOS PARA LA VISTA Y EL INICIALIZADOR
    private String turno;
    private String nivel;          // nuevo, intermedio, experto
    private String areaAsignada;  // salon, exterior, barra o mas de uno

    // RELACIÓN CON PEDIDOS (Ignorada en JSON para evitar el error 500 de bucle infinito)
    @JsonIgnore
    @OneToMany(mappedBy = "mesero")
    private List<Pedido> pedidos;

    // Constructor vacío obligatorio para el funcionamiento interno de JPA
    public Mesero() {}

    // 1. CONSTRUCTOR ANTIGUO (Se mantiene intacto para no romper la compatibilidad con el código previo)
    public Mesero(double salario, Date fechaVinculacion, Time horaIngreso, Time horaSalida,
                  String nombre, String cedula, String telefono) {
        super(fechaVinculacion, horaIngreso, horaSalida, nombre, cedula, telefono);
        this.salario = salario;
    }

    // 2. NUEVO CONSTRUCTOR (El que utiliza el DataInitializer)
    public Mesero(String nombre, String turno, String nivel, String areaAsignada, double salario) {
        super(); // Invoca al constructor vacío de la clase padre Empleado
        this.setNombre(nombre); // Asigna el nombre a través de la herencia de Persona
        this.turno = turno;
        this.nivel = nivel;
        this.areaAsignada = areaAsignada;
        this.salario = salario;
    }

    // Getters y Setters tradicionales
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    // NUEVOS Getters y Setters para mapear los datos en el Frontend
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getAreaAsignada() { return areaAsignada; }
    public void setAreaAsignada(String areaAsignada) { this.areaAsignada = areaAsignada; }

    // Getters y Setters de la lista de Pedidos
    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }

    @Override
    public String toString() { return getNombre(); }
}