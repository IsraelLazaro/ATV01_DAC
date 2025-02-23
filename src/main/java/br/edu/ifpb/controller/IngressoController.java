package br.edu.ifpb.controller;

import br.edu.ifpb.model.Ingresso;
import br.edu.ifpb.service.IngressoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/ingressos")
public class IngressoController {
    @Autowired
    private IngressoService ingressoService;
    @PostMapping
    public Ingresso criarIngresso(@RequestBody Ingresso ingresso) {
        return ingressoService.criarIngresso(ingresso);
    }

    @GetMapping("/{eventoId}")
    public List<Ingresso> consultarIngressosPorEvento(
            @PathVariable Long eventoId,
            @RequestParam(required = false) String modalidade) {
        return ingressoService.consultarIngressos(eventoId, modalidade);
}

    @GetMapping
    public List<Ingresso> consultarIngressos(
            @RequestParam(required = false) Long eventoId,
            @RequestParam(required = false) String modalidade) {
        return ingressoService.consultarIngressos(eventoId, modalidade);
    }
}