package com.mycompany.restaurante.dto.response;

import com.mycompany.restaurante.model.Alimento;

public class AlimentoResponseDTO {

    private int id;
    private String nombre;
    private double precio;
    private String tipo;

    public AlimentoResponseDTO() {}

    public AlimentoResponseDTO(int id, String nombre, double precio, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
    }

    // Factory method - igual que en el proyecto Multas
    public static AlimentoResponseDTO from(Alimento alimento) {
        return new AlimentoResponseDTO(
                alimento.getId(),
                alimento.getNombre(),
                alimento.getPrecio(),
                alimento.getClass().getSimpleName()
        );
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
