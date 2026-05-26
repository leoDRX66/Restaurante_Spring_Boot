package com.mycompany.restaurante.repository;

import com.mycompany.restaurante.model.Mesero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeseroRepository extends JpaRepository<Mesero, Integer> {
}
