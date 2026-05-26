package com.mycompany.restaurante.service;

import com.mycompany.restaurante.model.Pedido;
import java.util.List;

public interface PedidoService extends CrudService<Pedido, Integer> {

    List<Pedido> listarPorMesa(int numeroMesa);

    double calcularTotal(int idPlato, Integer idBebida) throws Exception;
}
