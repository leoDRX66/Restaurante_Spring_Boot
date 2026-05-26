package com.mycompany.restaurante.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PlatoFuerte")
public class PlatoFuerte extends Alimento {

    public PlatoFuerte() {}

    public PlatoFuerte(String nombre, double precio) {
        super(nombre, precio);
    }
}
