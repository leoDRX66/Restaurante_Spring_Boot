package com.mycompany.restaurante.controller;

import com.mycompany.restaurante.dto.request.PedidoRequestDTO;
import com.mycompany.restaurante.dto.response.PedidoResponseDTO;
import com.mycompany.restaurante.model.Alimento;
import com.mycompany.restaurante.model.Chef;
import com.mycompany.restaurante.model.Mesero;
import com.mycompany.restaurante.model.Pedido;
import com.mycompany.restaurante.service.AlimentoService;
import com.mycompany.restaurante.service.ChefService;
import com.mycompany.restaurante.service.MeseroService;
import com.mycompany.restaurante.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ChefService chefService;

    @Autowired
    private MeseroService meseroService;

    @Autowired
    private AlimentoService alimentoService;

    // GET /api/pedidos  -> listar todos los pedidos
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        List<PedidoResponseDTO> response = pedidoService.listar().stream()
                .map(PedidoResponseDTO::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    // GET /api/pedidos/{id}  -> buscar pedido por id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPedido(@PathVariable int id) {
        try {
            Pedido pedido = pedidoService.listarPorId(id);
            return ResponseEntity.ok(PedidoResponseDTO.from(pedido));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET /api/pedidos/mesa/{numero}  -> pedidos por número de mesa
    @GetMapping("/mesa/{numero}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorMesa(@PathVariable int numero) {
        List<PedidoResponseDTO> response = pedidoService.listarPorMesa(numero).stream()
                .map(PedidoResponseDTO::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    // POST /api/pedidos  -> crear nuevo pedido
    @PostMapping
    public ResponseEntity<?> crearPedido(@Valid @RequestBody PedidoRequestDTO dto) {
        try {
            Chef chef = chefService.listarPorId(dto.getChefId());
            Mesero mesero = meseroService.listarPorId(dto.getMeseroId());
            Alimento plato = alimentoService.listarPorId(dto.getPlatoId());

            Pedido pedido = new Pedido();
            pedido.setChef(chef);
            pedido.setMesero(mesero);
            pedido.setPlato(plato);
            pedido.setNumeroMesa(dto.getNumeroMesa());

            if (dto.getBebidaId() != null) {
                Alimento bebida = alimentoService.listarPorId(dto.getBebidaId());
                pedido.setBebida(bebida);
            }

            pedidoService.crear(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pedido registrado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // DELETE /api/pedidos/{id}  -> entregar/eliminar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPedido(@PathVariable int id) {
        try {
            pedidoService.eliminar(id);
            return ResponseEntity.ok("Pedido entregado y eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
