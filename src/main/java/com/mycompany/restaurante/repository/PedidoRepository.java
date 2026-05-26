package com.mycompany.restaurante.repository;

import com.mycompany.restaurante.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByNumeroMesa(int numeroMesa);

    boolean existsByChef_Id(int chefId);

    boolean existsByMesero_Id(int meseroId);
}
