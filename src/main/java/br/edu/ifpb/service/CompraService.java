package br.edu.ifpb.service;

import br.edu.ifpb.model.Compra;
import br.edu.ifpb.model.Evento;
import br.edu.ifpb.model.Ingresso;
import br.edu.ifpb.model.Usuario;
import br.edu.ifpb.repository.CompraRepository;
import br.edu.ifpb.repository.IngressoRepository;
import br.edu.ifpb.repository.UsuarioRepository;
import br.edu.ifpb.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class CompraService {
    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EmailService emailService; // Injete o EmailService

    @Transactional
    public Compra realizarCompra(Compra compra) {
        if (compra.getUsuario() == null || compra.getUsuario().getId() == null) {
            throw new IllegalArgumentException("O usuário da compra é obrigatório.");
        }
        if (compra.getEvento() == null || compra.getEvento().getId() == null) {
            throw new IllegalArgumentException("O evento da compra é obrigatório.");
        }
        if (compra.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade da compra deve ser maior que zero.");
        }
        if (compra.getModalidade() == null || compra.getModalidade().trim().isEmpty()) {
            throw new IllegalArgumentException("A modalidade da compra é obrigatória.");
        }

        Usuario usuario = usuarioRepository.findById(compra.getUsuario().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        Evento evento = eventoRepository.findById(compra.getEvento().getId())
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado."));
        Ingresso ingresso = ingressoRepository.findByEventoAndModalidade(evento, compra.getModalidade())
                .orElseThrow(() -> new IllegalArgumentException("Ingresso não encontrado para a modalidade especificada."));

        if (ingresso.getQuantidade() < compra.getQuantidade()) {
            throw new IllegalArgumentException("Ingressos insuficientes para a modalidade " + compra.getModalidade() + ".");
        }

        BigDecimal valorTotal = BigDecimal.valueOf(ingresso.getPreco()).multiply(BigDecimal.valueOf(compra.getQuantidade()));
        compra.setValorTotal(valorTotal);
        compra.setDataCompra(LocalDateTime.now());
        ingresso.setQuantidade(ingresso.getQuantidade() - compra.getQuantidade());
        ingressoRepository.save(ingresso);
        compra.setUsuario(usuario);
        compra.setEvento(evento);
        Compra compraSalva = compraRepository.save(compra);

        enviarEmailConfirmacao(usuario, compraSalva);
        return compraSalva;
    }

    private void enviarEmailConfirmacao(Usuario usuario, Compra compra) {
        String assunto = "Confirmação de Compra";
        String corpo = String.format(
            "Olá, %s! Sua compra foi realizada com sucesso.\n" +
            "Detalhes da compra:\n" +
            "Evento: %s\n" +
            "Modalidade: %s\n" +
            "Quantidade: %d\n" +
            "Valor Total: R$ %.2f\n" +
            "\n Obrigado pela preferência!\n",
            usuario.getNome(),
            compra.getEvento().getNome(),
            compra.getModalidade(),
            compra.getQuantidade(),
            compra.getValorTotal()
        );

        try {
            emailService.enviarEmail(usuario.getEmail(), assunto, corpo);
        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }

    public List<Compra> consultarComprasPorUsuario(Long usuarioId) {
        return compraRepository.findByUsuario_Id(usuarioId);
    }

    public List<Compra> consultarComprasPorModalidade(String modalidade) {
        return compraRepository.findByModalidade(modalidade);
    }

    public List<Compra> consultarComprasPorUsuarioEModalidade(Long usuarioId, String modalidade) {
        return compraRepository.findByUsuario_IdAndModalidade(usuarioId, modalidade);
    }

    public boolean existeConflitoDeHorarioELocal(LocalDate dataInicio, LocalTime horario, String local) {
        return eventoRepository.existsByDataInicioAndHorarioAndLocal(dataInicio, horario, local);
    }

    public List<Evento> consultarEventos(String categoria, LocalDate data, String local) {
        if (categoria != null && data != null && local != null) {
            return eventoRepository.findByCategoriaAndDataInicioAndLocal(categoria, data, local);
        } else if (categoria != null && data != null) {
            return eventoRepository.findByCategoriaAndDataInicio(categoria, data);
        } else if (categoria != null && local != null) {
            return eventoRepository.findByCategoriaAndLocal(categoria, local);
        } else if (data != null && local != null) {
            return eventoRepository.findByDataInicioAndLocal(data, local);
        } else if (categoria != null) {
            return eventoRepository.findByCategoria(categoria);
        } else if (data != null) {
            return eventoRepository.findByDataInicio(data);
        } else if (local != null) {
            return eventoRepository.findByLocal(local);
        } else {
            return eventoRepository.findAll();
        }
    }
}