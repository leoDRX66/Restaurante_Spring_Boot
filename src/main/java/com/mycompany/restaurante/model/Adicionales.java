package com.mycompany.restaurante.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Adicional")
public class Adicionales extends Alimento {

    public Adicionales() {}

    public Adicionales(String nombre, double precio) {
        super(nombre, precio);
    }
}
