package com.mycompany.restaurante.service;

import com.mycompany.restaurante.model.Alimento;
import com.mycompany.restaurante.model.Pedido;
import com.mycompany.restaurante.repository.AlimentoRepository;
import com.mycompany.restaurante.repository.ChefRepository;
import com.mycompany.restaurante.repository.MeseroRepository;
import com.mycompany.restaurante.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private ChefRepository chefRepository;

    @Autowired
    private MeseroRepository meseroRepository;

    @Override
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido listarPorId(Integer id) throws Exception {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe un pedido con id: " + id));
    }

    @Override
    @Transactional
    public void crear(Pedido pedido) throws Exception {
        if (pedido == null) {
            throw new Exception("El pedido no puede ser nulo");
        }
        if (pedido.getChef() == null) {
            throw new Exception("El pedido debe tener un chef asignado");
        }
        if (pedido.getMesero() == null) {
            throw new Exception("El pedido debe tener un mesero asignado");
        }
        if (pedido.getPlato() == null) {
            throw new Exception("El pedido debe tener al menos un plato");
        }
        if (pedido.getNumeroMesa() <= 0) {
            throw new Exception("El número de mesa debe ser mayor a 0");
        }

        // Calcular total automáticamente
        double total = pedido.getPlato().getPrecio();
        if (pedido.getBebida() != null) {
            total += pedido.getBebida().getPrecio();
        }
        pedido.setTotal(total);

        pedidoRepository.save(pedido);
    }

    @Override
    public void actualizar(Pedido pedido) throws Exception {
        if (pedido == null || pedido.getId() == 0) {
            throw new Exception("Pedido inválido para actualizar");
        }
        pedidoRepository.save(pedido);
    }

    @Override
    public void eliminar(Integer id) throws Exception {
        if (!pedidoRepository.existsById(id)) {
            throw new Exception("No existe un pedido con id: " + id);
        }
        pedidoRepository.deleteById(id);
    }

    @Override
    public List<Pedido> listarPorMesa(int numeroMesa) {
        return pedidoRepository.findByNumeroMesa(numeroMesa);
    }

    @Override
    public double calcularTotal(int idPlato, Integer idBebida) throws Exception {
        Alimento plato = alimentoRepository.findById(idPlato)
                .orElseThrow(() -> new Exception("No existe el plato con id: " + idPlato));
        double total = plato.getPrecio();
        if (idBebida != null) {
            Alimento bebida = alimentoRepository.findById(idBebida)
                    .orElseThrow(() -> new Exception("No existe la bebida con id: " + idBebida));
            total += bebida.getPrecio();
        }
        return total;
    }
}
