package com.mycompany.restaurante.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pedidos_detalle")
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_chef")
    private Chef chef;

    @ManyToOne
    @JoinColumn(name = "id_mesero")
    private Mesero mesero;

    @Column(name = "numero_mesa")
    private int numeroMesa;

    @ManyToOne
    @JoinColumn(name = "id_plato")
    private Alimento plato;

    @ManyToOne
    @JoinColumn(name = "id_bebida")
    private Alimento bebida;

    private double total;

    public Pedido() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Chef getChef() { return chef; }
    public void setChef(Chef chef) { this.chef = chef; }

    public Mesero getMesero() { return mesero; }
    public void setMesero(Mesero mesero) { this.mesero = mesero; }

    public int getNumeroMesa() { return numeroMesa; }
    public void setNumeroMesa(int numeroMesa) { this.numeroMesa = numeroMesa; }

    public Alimento getPlato() { return plato; }
    public void setPlato(Alimento plato) { this.plato = plato; }

    public Alimento getBebida() { return bebida; }
    public void setBebida(Alimento bebida) { this.bebida = bebida; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
