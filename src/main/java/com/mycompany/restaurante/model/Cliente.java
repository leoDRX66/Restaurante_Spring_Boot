package com.mycompany.restaurante.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Persona {

    public Cliente() {}

    public Cliente(String nombre, String cedula, String telefono) {
        super(nombre, cedula, telefono);
    }
}
