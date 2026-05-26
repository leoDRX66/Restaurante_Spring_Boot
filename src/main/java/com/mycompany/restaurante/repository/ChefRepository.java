package com.mycompany.restaurante.repository;

import com.mycompany.restaurante.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Integer> {
}
