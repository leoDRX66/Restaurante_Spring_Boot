package com.mycompany.restaurante.dto.request;

import jakarta.validation.constraints.Positive;

public class PedidoRequestDTO {

    @Positive(message = "El id del chef es obligatorio")
    private int chefId;

    @Positive(message = "El id del mesero es obligatorio")
    private int meseroId;

    @Positive(message = "El número de mesa debe ser mayor a 0")
    private int numeroMesa;

    @Positive(message = "El id del plato es obligatorio")
    private int platoId;

    // La bebida es opcional (puede ser null)
    private Integer bebidaId;

    public PedidoRequestDTO() {}

    public int getChefId() { return chefId; }
    public void setChefId(int chefId) { this.chefId = chefId; }

    public int getMeseroId() { return meseroId; }
    public void setMeseroId(int meseroId) { this.meseroId = meseroId; }

    public int getNumeroMesa() { return numeroMesa; }
    public void setNumeroMesa(int numeroMesa) { this.numeroMesa = numeroMesa; }

    public int getPlatoId() { return platoId; }
    public void setPlatoId(int platoId) { this.platoId = platoId; }

    public Integer getBebidaId() { return bebidaId; }
    public void setBebidaId(Integer bebidaId) { this.bebidaId = bebidaId; }
}
