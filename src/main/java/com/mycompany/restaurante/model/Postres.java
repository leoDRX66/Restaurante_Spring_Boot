package com.mycompany.restaurante.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Postre")
public class Postres extends Alimento {

    public Postres() {}

    public Postres(String nombre, double precio) {
        super(nombre, precio);
    }
}
