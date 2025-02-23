package br.edu.ifpb.testesProjeto;

import br.edu.ifpb.model.Compra;
import br.edu.ifpb.model.Evento;
import br.edu.ifpb.model.Ingresso;
import br.edu.ifpb.model.Usuario;
import br.edu.ifpb.repository.CompraRepository;
import br.edu.ifpb.repository.EventoRepository;
import br.edu.ifpb.repository.IngressoRepository;
import br.edu.ifpb.repository.UsuarioRepository;
import br.edu.ifpb.service.CompraService;
import br.edu.ifpb.service.EventoService;
import br.edu.ifpb.service.IngressoService;
import br.edu.ifpb.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TestesUnitarios {

    @Mock
    private CompraRepository compraRepository;

    @Mock
    private IngressoRepository ingressoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private CompraService compraService;

    @InjectMocks
    private EventoService eventoService;

    @InjectMocks
    private IngressoService ingressoService;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Testes para CompraService
    @Test
    void realizarCompra_DeveRetornarCompraSalva_QuandoDadosValidos() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");
        usuario.setEmail("joao@example.com");

        Evento evento = new Evento();
        evento.setId(1L);
        evento.setNome("Show");
        evento.setCapacidade(100);

        Ingresso ingresso = new Ingresso();
        ingresso.setId(1L);
        ingresso.setEvento(evento);
        ingresso.setModalidade("Pista");
        ingresso.setPreco(100.0);
        ingresso.setQuantidade(50);

        Compra compra = new Compra();
        compra.setUsuario(usuario);
        compra.setEvento(evento);
        compra.setQuantidade(2);
        compra.setModalidade("Pista");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));
        when(ingressoRepository.findByEventoAndModalidade(evento, "Pista")).thenReturn(Optional.of(ingresso));
        when(compraRepository.save(any(Compra.class))).thenReturn(compra);

        // Act
        Compra compraSalva = compraService.realizarCompra(compra);

        // Assert
        assertNotNull(compraSalva);
        assertEquals(0, new BigDecimal("200.00").compareTo(compraSalva.getValorTotal()));
    }

    @Test
    void realizarCompra_DeveLancarExcecao_QuandoIngressosInsuficientes() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");
        usuario.setEmail("joao@example.com");

        Evento evento = new Evento();
        evento.setId(1L);
        evento.setNome("Show");
        evento.setCapacidade(100);

        Ingresso ingresso = new Ingresso();
        ingresso.setId(1L);
        ingresso.setEvento(evento);
        ingresso.setModalidade("Pista");
        ingresso.setPreco(100.0);
        ingresso.setQuantidade(1); // Quantidade insuficiente

        Compra compra = new Compra();
        compra.setUsuario(usuario);
        compra.setEvento(evento);
        compra.setQuantidade(2); // Quantidade maior que a disponível
        compra.setModalidade("Pista");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));
        when(ingressoRepository.findByEventoAndModalidade(evento, "Pista")).thenReturn(Optional.of(ingresso));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> compraService.realizarCompra(compra));
    }

    // Testes para EventoService
    @Test
    void criarEvento_DeveRetornarEventoSalvo_QuandoDadosValidos() {
        // Arrange
        Evento evento = new Evento();
        evento.setId(1L);
        evento.setNome("Show");
        evento.setLocal("Local A");
        evento.setCapacidade(100);
        evento.setDataInicio(LocalDate.now());
        evento.setHorario(LocalTime.now());

        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        // Act
        Evento eventoSave = eventoService.criarEvento(evento);

        // Assert
        assertNotNull(eventoSave);
        assertEquals("Show", eventoSave.getNome());
    }

    @Test
    void criarEvento_DeveLancarExcecao_QuandoConflitoDeHorario() {
        // Arrange
        Evento evento = new Evento();
        evento.setId(1L);
        evento.setNome("Show");
        evento.setLocal("Local A");
        evento.setCapacidade(100);
        evento.setDataInicio(LocalDate.now());
        evento.setHorario(LocalTime.now());

        when(eventoRepository.findByLocalAndHorario(evento.getHorario(), evento.getLocal())).thenReturn(List.of(evento));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> eventoService.criarEvento(evento));
    }

    // Testes para IngressoService
    @Test
    void criarIngresso_DeveRetornarIngressoSalvo_QuandoDadosValidos() {
        // Arrange
        Evento evento = new Evento();
        evento.setId(1L);
        evento.setNome("Show");
        evento.setCapacidade(100);

        Ingresso ingresso = new Ingresso();
        ingresso.setId(1L);
        ingresso.setEvento(evento);
        ingresso.setModalidade("Pista");
        ingresso.setPreco(100.0);
        ingresso.setQuantidade(50);

        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));
        when(ingressoRepository.save(any(Ingresso.class))).thenReturn(ingresso);

        // Act
        Ingresso ingressoSalvo = ingressoService.criarIngresso(ingresso);

        // Assert
        assertNotNull(ingressoSalvo);
        assertEquals("Pista", ingressoSalvo.getModalidade());
    }

    @Test
    void criarIngresso_DeveLancarExcecao_QuandoQuantidadeInvalida() {
        // Arrange
        Evento evento = new Evento();
        evento.setId(1L);
        evento.setNome("Show");
        evento.setCapacidade(100);

        Ingresso ingresso = new Ingresso();
        ingresso.setId(1L);
        ingresso.setEvento(evento);
        ingresso.setModalidade("Pista");
        ingresso.setPreco(100.0);
        ingresso.setQuantidade(0); // Quantidade inválida

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> ingressoService.criarIngresso(ingresso));
    }

    // Testes para UsuarioService
    @Test
    void criarUsuario_DeveRetornarUsuarioSalvo_QuandoDadosValidos() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");
        usuario.setEmail("joao@example.com");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        Usuario usuarioSalvo = usuarioService.criarUsuario(usuario);

        // Assert
        assertNotNull(usuarioSalvo);
        assertEquals("João", usuarioSalvo.getNome());
    }

    @Test
    void criarUsuario_DeveLancarExcecao_QuandoEmailJaExiste() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");
        usuario.setEmail("joao@example.com");

        when(usuarioRepository.findByEmail("joao@example.com")).thenReturn(Optional.of(usuario));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> usuarioService.criarUsuario(usuario));
    }
}