package br.edu.ifpb.controller;

import br.edu.ifpb.model.Evento;
import br.edu.ifpb.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @PostMapping
    public Evento criarEvento(@RequestBody Evento evento) {
        return eventoService.criarEvento(evento);
    }
    @DeleteMapping("/{id}")
    public void cancelarEvento(@PathVariable Long id) {
        eventoService.cancelarEvento(id);
    }    

    @GetMapping
    public List<Evento> consultarEventos(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) LocalDate data,
            @RequestParam(required = false) String local) {
        return eventoService.consultarEventos(categoria, data, local);
    }
}