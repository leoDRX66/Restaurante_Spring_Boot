package com.mycompany.restaurante.service;

import com.mycompany.restaurante.model.Alimento;
import com.mycompany.restaurante.repository.AlimentoRepository;
import com.mycompany.restaurante.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlimentoServiceImpl implements AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public List<Alimento> listar() {
        return alimentoRepository.findAll();
    }

    @Override
    public Alimento listarPorId(Integer id) throws Exception {
        return alimentoRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe un alimento con id: " + id));
    }

    @Override
    public void crear(Alimento alimento) throws Exception {
        if (alimento == null) {
            throw new Exception("El alimento no puede ser nulo");
        }
        if (alimento.getNombre() == null || alimento.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del alimento es obligatorio");
        }
        if (alimento.getPrecio() <= 0) {
            throw new Exception("El precio debe ser mayor a 0");
        }
        alimentoRepository.save(alimento);
    }

    @Override
    public void actualizar(Alimento alimento) throws Exception {
        if (alimento == null || alimento.getId() == 0) {
            throw new Exception("Alimento inválido para actualizar");
        }
        alimentoRepository.save(alimento);
    }

    @Override
    public void eliminar(Integer id) throws Exception {
        if (!alimentoRepository.existsById(id)) {
            throw new Exception("No existe un alimento con id: " + id);
        }
        alimentoRepository.deleteById(id);
    }
}
