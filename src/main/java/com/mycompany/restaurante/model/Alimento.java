package com.mycompany.restaurante.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "alimentos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_alimento")
public abstract class Alimento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private double precio;

    public Alimento() {}

    public Alimento(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public String toString() {
        return nombre + " ($" + precio + ")";
    }
}
