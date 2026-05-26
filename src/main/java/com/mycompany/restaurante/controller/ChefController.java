package com.mycompany.restaurante.controller;

import com.mycompany.restaurante.dto.request.ChefRequestDTO;
import com.mycompany.restaurante.dto.response.ChefResponseDTO;
import com.mycompany.restaurante.service.ChefService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/chefs")
public class ChefController {

    @Autowired
    private ChefService chefService;

    // GET /api/chefs  -> listar todos los chefs
    @GetMapping
    public ResponseEntity<List<ChefResponseDTO>> listarChefs() {
        List<ChefResponseDTO> response = chefService.listar().stream()
                .map(ChefResponseDTO::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    // GET /api/chefs/{id}  -> buscar chef por id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarChef(@PathVariable int id) {
        try {
            return ResponseEntity.ok(ChefResponseDTO.from(chefService.listarPorId(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // POST /api/chefs  -> crear nuevo chef
    @PostMapping
    public ResponseEntity<?> crearChef(@Valid @RequestBody ChefRequestDTO dto) {
        try {
            chefService.crear(dto.toEntity());
            return ResponseEntity.status(HttpStatus.CREATED).body("Chef creado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // DELETE /api/chefs/{id}  -> eliminar chef
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarChef(@PathVariable int id) {
        try {
            chefService.eliminar(id);
            return ResponseEntity.ok("Chef eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
