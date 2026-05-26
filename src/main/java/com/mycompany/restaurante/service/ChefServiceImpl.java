package com.mycompany.restaurante.service;

import com.mycompany.restaurante.model.Chef;
import com.mycompany.restaurante.repository.ChefRepository;
import com.mycompany.restaurante.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChefServiceImpl implements ChefService {

    @Autowired
    private ChefRepository chefRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public List<Chef> listar() {
        return chefRepository.findAll();
    }

    @Override
    public Chef listarPorId(Integer id) throws Exception {
        return chefRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe un chef con id: " + id));
    }

    @Override
    public void crear(Chef chef) throws Exception {
        if (chef == null) {
            throw new Exception("El chef no puede ser nulo");
        }
        if (chef.getNombre() == null || chef.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del chef es obligatorio");
        }
        if (chef.getCedula() == null || chef.getCedula().trim().isEmpty()) {
            throw new Exception("La cédula del chef es obligatoria");
        }
        if (chef.getSalario() <= 0) {
            throw new Exception("El salario debe ser mayor a 0");
        }
        chefRepository.save(chef);
    }

    @Override
    public void actualizar(Chef chef) throws Exception {
        if (chef == null || chef.getId() == 0) {
            throw new Exception("Chef inválido para actualizar");
        }
        chefRepository.save(chef);
    }

    @Override
    public void eliminar(Integer id) throws Exception {
        if (!chefRepository.existsById(id)) {
            throw new Exception("No existe un chef con id: " + id);
        }
        if (pedidoRepository.existsByChef_Id(id)) {
            throw new Exception("No se puede eliminar: el chef tiene pedidos asociados");
        }
        chefRepository.deleteById(id);
    }
}
