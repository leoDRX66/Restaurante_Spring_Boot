package com.mycompany.restaurante.dto.request;

import com.mycompany.restaurante.model.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class AlimentoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Positive(message = "El precio debe ser mayor a 0")
    private double precio;

    // Tipo: "Bebida", "PlatoFuerte", "Postre", "Adicional"
    @NotBlank(message = "El tipo de alimento es obligatorio")
    private String tipo;

    public AlimentoRequestDTO() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Alimento toEntity() throws Exception {
        return switch (tipo) {
            case "Bebida"      -> new Bebida(nombre, precio);
            case "PlatoFuerte" -> new PlatoFuerte(nombre, precio);
            case "Postre"      -> new Postres(nombre, precio);
            case "Adicional"   -> new Adicionales(nombre, precio);
            default -> throw new Exception("Tipo de alimento no válido: " + tipo
                    + ". Use: Bebida, PlatoFuerte, Postre, Adicional");
        };
    }
}
