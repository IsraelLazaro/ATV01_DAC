package br.edu.ifpb.service;

import br.edu.ifpb.model.Evento;
import br.edu.ifpb.model.Ingresso;
import br.edu.ifpb.repository.EventoRepository;
import br.edu.ifpb.repository.IngressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class IngressoService {
    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Transactional
    public Ingresso criarIngresso(Ingresso ingresso) {
        if (ingresso.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade de ingressos deve ser maior que zero.");
        }
        if (ingresso.getPreco() <= 0) {
            throw new IllegalArgumentException("O preço do ingresso deve ser maior que zero.");
        }
        Evento evento = ingresso.getEvento();
        if (evento == null || evento.getId() == null) {
            throw new IllegalArgumentException("Evento associado ao ingresso é inválido.");
        }

        Optional<Evento> eventoOptional = eventoRepository.findById(evento.getId());
            if (eventoOptional.isEmpty()) {
                throw new IllegalArgumentException("Evento não encontrado.");
            }
        Evento eventoExistente = eventoOptional.get();
            if (eventoExistente.getCapacidade() < ingresso.getQuantidade()) {
                throw new IllegalArgumentException("Quantidade de ingressos indisponível para o evento.");
            }
        Ingresso ingressoSalvo = ingressoRepository.save(ingresso);
        
        eventoExistente.setCapacidade(eventoExistente.getCapacidade() - ingresso.getQuantidade());
        eventoRepository.save(eventoExistente);
        return ingressoSalvo;
    }

    public List<Ingresso> consultarIngressos(Long eventoId, String modalidade) {
        return ingressoRepository.findByEventoIdAndModalidade(eventoId, modalidade);
    }
}