package com.mycompany.restaurante.dto.response;

import com.mycompany.restaurante.model.Pedido;

public class PedidoResponseDTO {

    private int id;

    private int chefId;
    private String chefNombre;

    private int meseroId;
    private String meseroNombre;

    private int platoId;
    private String platoNombre;

    private Integer bebidaId;
    private String bebidaNombre;

    private int numeroMesa;

    public PedidoResponseDTO() {}

    public static PedidoResponseDTO from(Pedido pedido) {

        PedidoResponseDTO dto = new PedidoResponseDTO();

        dto.setId(pedido.getId());

        // CHEF
        if (pedido.getChef() != null) {
            dto.setChefId(pedido.getChef().getId());
            dto.setChefNombre(pedido.getChef().getNombre());
        }

        // MESERO
        if (pedido.getMesero() != null) {
            dto.setMeseroId(pedido.getMesero().getId());
            dto.setMeseroNombre(pedido.getMesero().getNombre());
        }

        // PLATO
        if (pedido.getPlato() != null) {
            dto.setPlatoId(pedido.getPlato().getId());
            dto.setPlatoNombre(pedido.getPlato().getNombre());
        }

        // BEBIDA
        if (pedido.getBebida() != null) {
            dto.setBebidaId(pedido.getBebida().getId());
            dto.setBebidaNombre(pedido.getBebida().getNombre());
        }

        dto.setNumeroMesa(pedido.getNumeroMesa());

        return dto;
    }

    // GETTERS Y SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChefId() {
        return chefId;
    }

    public void setChefId(int chefId) {
        this.chefId = chefId;
    }

    public String getChefNombre() {
        return chefNombre;
    }

    public void setChefNombre(String chefNombre) {
        this.chefNombre = chefNombre;
    }

    public int getMeseroId() {
        return meseroId;
    }

    public void setMeseroId(int meseroId) {
        this.meseroId = meseroId;
    }

    public String getMeseroNombre() {
        return meseroNombre;
    }

    public void setMeseroNombre(String meseroNombre) {
        this.meseroNombre = meseroNombre;
    }

    public int getPlatoId() {
        return platoId;
    }

    public void setPlatoId(int platoId) {
        this.platoId = platoId;
    }

    public String getPlatoNombre() {
        return platoNombre;
    }

    public void setPlatoNombre(String platoNombre) {
        this.platoNombre = platoNombre;
    }

    public Integer getBebidaId() {
        return bebidaId;
    }

    public void setBebidaId(Integer bebidaId) {
        this.bebidaId = bebidaId;
    }

    public String getBebidaNombre() {
        return bebidaNombre;
    }

    public void setBebidaNombre(String bebidaNombre) {
        this.bebidaNombre = bebidaNombre;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }
}