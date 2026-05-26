package com.mycompany.restaurante.controller;

import com.mycompany.restaurante.dto.request.MeseroRequestDTO;
import com.mycompany.restaurante.dto.response.MeseroResponseDTO;
import com.mycompany.restaurante.service.MeseroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/meseros")
public class MeseroController {

    @Autowired
    private MeseroService meseroService;

    // GET /api/meseros  -> listar todos los meseros
    @GetMapping
    public ResponseEntity<List<MeseroResponseDTO>> listarMeseros() {
        List<MeseroResponseDTO> response = meseroService.listar().stream()
                .map(MeseroResponseDTO::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    // GET /api/meseros/{id}  -> buscar mesero por id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarMesero(@PathVariable int id) {
        try {
            return ResponseEntity.ok(MeseroResponseDTO.from(meseroService.listarPorId(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // POST /api/meseros  -> crear nuevo mesero
    @PostMapping
    public ResponseEntity<?> crearMesero(@Valid @RequestBody MeseroRequestDTO dto) {
        try {
            meseroService.crear(dto.toEntity());
            return ResponseEntity.status(HttpStatus.CREATED).body("Mesero creado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // DELETE /api/meseros/{id}  -> eliminar mesero
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMesero(@PathVariable int id) {
        try {
            meseroService.eliminar(id);
            return ResponseEntity.ok("Mesero eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
