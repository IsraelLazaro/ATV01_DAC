package br.edu.ifpb.service;

import br.edu.ifpb.model.Evento;
import br.edu.ifpb.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    public Evento criarEvento(Evento evento) {
            if (verificarConflitoDeHorarioElocal(evento)) {
                throw new IllegalArgumentException("Já existe um evento neste local e horário.");
            }
        try {
            Evento eventoSalvo = eventoRepository.save(evento);
            return eventoSalvo;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar evento: " + e.getMessage(), e);
        }
    }

    public void cancelarEvento(long id){
        try {
            if (!eventoRepository.existsById(id)) {
                throw new IllegalArgumentException("O evento não existe.");
            }
            eventoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar evento: " + e.getMessage(), e);
        }
    }

    public List<Evento> consultarEventos(String categoria, LocalDate data, String local) {
        return eventoRepository.findByCategoriaAndDataInicioAndLocal(categoria, data, local);
    }

    private boolean verificarConflitoDeHorarioElocal(Evento evento) {
        List<Evento> eventosConflitantes = eventoRepository.findByLocalAndHorario(
            evento.getHorario(), evento.getLocal());
        return !eventosConflitantes.isEmpty();
    }
}