package com.mycompany.restaurante.controller;

import com.mycompany.restaurante.dto.request.AlimentoRequestDTO;
import com.mycompany.restaurante.dto.response.AlimentoResponseDTO;
import com.mycompany.restaurante.model.Alimento;
import com.mycompany.restaurante.service.AlimentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/alimentos")
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    // GET /api/alimentos  -> listar todos
    @GetMapping
    public ResponseEntity<List<AlimentoResponseDTO>> listarAlimentos() {
        List<AlimentoResponseDTO> response = alimentoService.listar().stream()
                .map(AlimentoResponseDTO::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    // GET /api/alimentos/{id}  -> buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarAlimento(@PathVariable int id) {
        try {
            Alimento alimento = alimentoService.listarPorId(id);
            return ResponseEntity.ok(AlimentoResponseDTO.from(alimento));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // POST /api/alimentos  -> crear nuevo alimento
    @PostMapping
    public ResponseEntity<?> crearAlimento(@Valid @RequestBody AlimentoRequestDTO dto) {
        try {
            Alimento alimento = dto.toEntity();
            alimentoService.crear(alimento);
            return ResponseEntity.status(HttpStatus.CREATED).body("Alimento guardado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // DELETE /api/alimentos/{id}  -> eliminar alimento
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAlimento(@PathVariable int id) {
        try {
            alimentoService.eliminar(id);
            return ResponseEntity.ok("Alimento eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // PATCH /api/alimentos/{id}/precio  -> actualizar precio
    @PatchMapping("/{id}/precio")
    public ResponseEntity<?> actualizarPrecio(@PathVariable int id,
                                               @RequestParam double precio) {
        try {
            Alimento alimento = alimentoService.listarPorId(id);
            alimento.setPrecio(precio);
            alimentoService.actualizar(alimento);
            return ResponseEntity.ok("Precio actualizado a $" + precio);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
