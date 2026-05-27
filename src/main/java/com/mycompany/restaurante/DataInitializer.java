package com.mycompany.restaurante;

import com.mycompany.restaurante.model.*;
import com.mycompany.restaurante.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ChefRepository chefRepository;

    @Autowired
    private MeseroRepository meseroRepository;

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Override
    public void run(String... args) throws Exception {

        // Solo carga datos si la base está vacía
        if (chefRepository.count() > 0) {
            System.out.println(">>> Base de datos ya tiene datos. Se omite inicialización.");
            return;
        }

        System.out.println(">>> Cargando datos iniciales...");

        // ---- CHEFS ----
        // Constructor actualizado: Chef(nombre, turno, especialidad, anosExperiencia, salario)
        Chef chef1 = new Chef("Juan Pérez", "11:00 - 18:30", "Asado y Carnes", 6, 5000.0);
        Chef chef2 = new Chef("María González", "18:30 - 02:00", "Mariscos y Pescados", 8, 4500.0);
        Chef chef3 = new Chef("Carlos López", "18:30 - 02:00", "Postres", 7, 4800.0);
        Chef chef4 = new Chef("Abigail Morgan", "18:30 - 02:00", "Postres", 3, 4800.0);

        chefRepository.save(chef1);
        chefRepository.save(chef2);
        chefRepository.save(chef3);
        chefRepository.save(chef4);

        // ---- MESEROS ----
        // Constructor actualizado: Mesero(nombre, turno, nivel, areaAsignada, propinaAcumulada)
        Mesero mesero2 = new Mesero("Ana Martínez", "12:00 - 22:00", "Experto", "Salón y exterior", 1200.0);
        Mesero mesero3 = new Mesero("Luis Rodríguez", "12:00 - 22:00", "Intermedio", "Terraza exterior", 1200.0);
        Mesero mesero5 = new Mesero("Camila Lopez", "18:00 - 02:00", "Nuevo", "Barra", 1300.0);
        Mesero mesero4 = new Mesero("Elena Vázquez", "18:00 - 02:00", "Intermedio", "Recepcionista", 1300.0);
        Mesero mesero1 = new Mesero("Nicol Ovalles", "18:00 - 02:00", "Experto", "Salon y terraza", 1300.0);

        meseroRepository.save(mesero1);
        meseroRepository.save(mesero2);
        meseroRepository.save(mesero3);
        meseroRepository.save(mesero4);
        meseroRepository.save(mesero5);

        System.out.println(">>> ¡Inyección de datos de personal completada con éxito!");
    }
}