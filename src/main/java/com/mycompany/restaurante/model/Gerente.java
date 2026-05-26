package com.mycompany.restaurante.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("GERENTE")
public class Gerente extends Persona {

    public Gerente() {}

    public Gerente(String nombre, String cedula, String telefono) {
        super(nombre, cedula, telefono);
    }
}
