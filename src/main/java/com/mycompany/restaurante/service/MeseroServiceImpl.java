package com.mycompany.restaurante.service;

import com.mycompany.restaurante.model.Mesero;
import com.mycompany.restaurante.repository.MeseroRepository;
import com.mycompany.restaurante.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeseroServiceImpl implements MeseroService {

    @Autowired
    private MeseroRepository meseroRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public List<Mesero> listar() {
        return meseroRepository.findAll();
    }

    @Override
    public Mesero listarPorId(Integer id) throws Exception {
        return meseroRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe un mesero con id: " + id));
    }

    @Override
    public void crear(Mesero mesero) throws Exception {
        if (mesero == null) {
            throw new Exception("El mesero no puede ser nulo");
        }
        if (mesero.getNombre() == null || mesero.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del mesero es obligatorio");
        }
        if (mesero.getCedula() == null || mesero.getCedula().trim().isEmpty()) {
            throw new Exception("La cédula del mesero es obligatoria");
        }
        if (mesero.getSalario() <= 0) {
            throw new Exception("El salario debe ser mayor a 0");
        }
        meseroRepository.save(mesero);
    }

    @Override
    public void actualizar(Mesero mesero) throws Exception {
        if (mesero == null || mesero.getId() == 0) {
            throw new Exception("Mesero inválido para actualizar");
        }
        meseroRepository.save(mesero);
    }

    @Override
    public void eliminar(Integer id) throws Exception {
        if (!meseroRepository.existsById(id)) {
            throw new Exception("No existe un mesero con id: " + id);
        }
        if (pedidoRepository.existsByMesero_Id(id)) {
            throw new Exception("No se puede eliminar: el mesero tiene pedidos asociados");
        }
        meseroRepository.deleteById(id);
    }
}
