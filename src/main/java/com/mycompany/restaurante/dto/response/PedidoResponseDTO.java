package com.mycompany.restaurante.dto.response;

import com.mycompany.restaurante.model.Pedido;

public class PedidoResponseDTO {

    private int id;
    private int numeroMesa;
    private String nombreChef;
    private String nombreMesero;
    private String nombrePlato;
    private double precioPlato;
    private String nombreBebida;
    private double precioBebida;
    private double total;

    public PedidoResponseDTO() {}

    public static PedidoResponseDTO from(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setNumeroMesa(pedido.getNumeroMesa());
        dto.setNombreChef(pedido.getChef() != null ? pedido.getChef().getNombre() : "N/A");
        dto.setNombreMesero(pedido.getMesero() != null ? pedido.getMesero().getNombre() : "N/A");
        dto.setNombrePlato(pedido.getPlato() != null ? pedido.getPlato().getNombre() : "N/A");
        dto.setPrecioPlato(pedido.getPlato() != null ? pedido.getPlato().getPrecio() : 0);
        dto.setNombreBebida(pedido.getBebida() != null ? pedido.getBebida().getNombre() : "Sin bebida");
        dto.setPrecioBebida(pedido.getBebida() != null ? pedido.getBebida().getPrecio() : 0);
        dto.setTotal(pedido.getTotal());
        return dto;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getNumeroMesa() { return numeroMesa; }
    public void setNumeroMesa(int numeroMesa) { this.numeroMesa = numeroMesa; }

    public String getNombreChef() { return nombreChef; }
    public void setNombreChef(String nombreChef) { this.nombreChef = nombreChef; }

    public String getNombreMesero() { return nombreMesero; }
    public void setNombreMesero(String nombreMesero) { this.nombreMesero = nombreMesero; }

    public String getNombrePlato() { return nombrePlato; }
    public void setNombrePlato(String nombrePlato) { this.nombrePlato = nombrePlato; }

    public double getPrecioPlato() { return precioPlato; }
    public void setPrecioPlato(double precioPlato) { this.precioPlato = precioPlato; }

    public String getNombreBebida() { return nombreBebida; }
    public void setNombreBebida(String nombreBebida) { this.nombreBebida = nombreBebida; }

    public double getPrecioBebida() { return precioBebida; }
    public void setPrecioBebida(double precioBebida) { this.precioBebida = precioBebida; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
