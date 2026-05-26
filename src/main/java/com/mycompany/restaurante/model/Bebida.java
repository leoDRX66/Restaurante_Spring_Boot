package com.mycompany.restaurante.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Bebida")
public class Bebida extends Alimento {

    public Bebida() {}

    public Bebida(String nombre, double precio) {
        super(nombre, precio);
    }
}
