package br.edu.ifpb.controller;

import br.edu.ifpb.model.Compra;
import br.edu.ifpb.model.Evento;
import br.edu.ifpb.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/compras")
public class CompraController {
    @Autowired
    private CompraService compraService;

    @PostMapping
    public ResponseEntity<Compra> realizarCompra(@RequestBody Compra compra) {
        Compra compraSalva = compraService.realizarCompra(compra);
        return ResponseEntity.ok(compraSalva);    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Compra>> consultarComprasPorUsuario(@PathVariable Long usuarioId) {
        List<Compra> compras = compraService.consultarComprasPorUsuario(usuarioId);
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/modalidade/{modalidade}")
    public ResponseEntity<List<Compra>> consultarComprasPorModalidade(@PathVariable String modalidade) {
        List<Compra> compras = compraService.consultarComprasPorModalidade(modalidade);
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/usuario/{usuarioId}/modalidade/{modalidade}")
    public ResponseEntity<List<Compra>> consultarComprasPorUsuarioEModalidade(
            @PathVariable Long usuarioId,
            @PathVariable String modalidade) {
        List<Compra> compras = compraService.consultarComprasPorUsuarioEModalidade(usuarioId, modalidade);
        return ResponseEntity.ok(compras);
    } 

    @GetMapping("/eventos/validar-conflito")
    public ResponseEntity<Boolean> validarConflitoDeHorarioELocal(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalTime horario,
            @RequestParam String local) {
        boolean conflito = compraService.existeConflitoDeHorarioELocal(dataInicio, horario, local);
        return ResponseEntity.ok(conflito);
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<Evento>> consultarEventos(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) LocalDate data,
            @RequestParam(required = false) String local) {
        List<Evento> eventos = compraService.consultarEventos(categoria, data, local);
        return ResponseEntity.ok(eventos);
    }
}