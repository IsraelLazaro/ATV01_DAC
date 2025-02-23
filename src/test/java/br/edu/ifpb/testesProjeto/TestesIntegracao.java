package br.edu.ifpb.testesProjeto;

import br.edu.ifpb.model.*;
import br.edu.ifpb.repository.*;
import br.edu.ifpb.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // Usa um perfil de teste (opcional, mas recomendado)
@Transactional // Garante que cada teste rode em uma transação e seja revertido ao final
public class TestesIntegracao {

    @Autowired
    private CompraService compraService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private IngressoService ingressoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private Evento evento;
    private Ingresso ingresso;

    @BeforeEach
    void setUp() {
        // Cria um usuário para os testes
        usuario = new Usuario();
        usuario.setNome("João Silva");
        usuario.setEmail("joao.silva@example.com");
        usuario.setSenha("senha123");
        usuario = usuarioRepository.save(usuario);

        // Cria um evento para os testes
        evento = new Evento();
        evento.setNome("Show da Banda XYZ");
        evento.setDescricao("Um show incrível!");
        evento.setLocal("Arena da Cidade");
        evento.setCapacidade(100);
        evento.setDataInicio(LocalDate.now().plusDays(10));
        evento.setHorario(LocalTime.of(20, 0));
        evento.setCategoria("Show");
        evento = eventoRepository.save(evento);

        // Cria um ingresso para o evento
        ingresso = new Ingresso();
        ingresso.setEvento(evento);
        ingresso.setModalidade("VIP");
        ingresso.setPreco(100.0);
        ingresso.setQuantidade(50);
        ingresso.setQuantidadeDisponivel(50);
        ingresso = ingressoRepository.save(ingresso);
    }

    @Test
    void realizarCompra_DeveSalvarCompra_QuandoDadosValidos() {
        // Arrange
        Compra compra = new Compra();
        compra.setUsuario(usuario);
        compra.setEvento(evento);
        compra.setQuantidade(2);
        compra.setModalidade("VIP");
        compra.setValorTotal(BigDecimal.valueOf(200.0));
        compra.setDataCompra(LocalDateTime.now());

        // Act
        Compra compraSalva = compraService.realizarCompra(compra);

        // Assert
        assertNotNull(compraSalva.getId());
        assertEquals(usuario, compraSalva.getUsuario());
        assertEquals(evento, compraSalva.getEvento());
        assertEquals(2, compraSalva.getQuantidade());
        assertEquals("VIP", compraSalva.getModalidade());
        assertEquals(BigDecimal.valueOf(200.0), compraSalva.getValorTotal());
    }

    @Test
    void realizarCompra_DeveLancarExcecao_QuandoIngressosInsuficientes() {
        // Arrange
        Compra compra = new Compra();
        compra.setUsuario(usuario);
        compra.setEvento(evento);
        compra.setQuantidade(100); // Quantidade maior que a disponível
        compra.setModalidade("VIP");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> compraService.realizarCompra(compra));
    }

    @Test
    void criarEvento_DeveSalvarEvento_QuandoDadosValidos() {
        // Arrange
        Evento novoEvento = new Evento();
        novoEvento.setNome("Festival de Música");
        novoEvento.setDescricao("Um festival incrível!");
        novoEvento.setLocal("Parque da Cidade");
        novoEvento.setCapacidade(500);
        novoEvento.setDataInicio(LocalDate.now().plusDays(15));
        novoEvento.setHorario(LocalTime.of(18, 0));
        novoEvento.setCategoria("Festival");

        // Act
        Evento eventoSavado = eventoService.criarEvento(novoEvento);

        // Assert
        assertNotNull(eventoSavado.getId());
        assertEquals("Festival de Música", eventoSavado.getNome());
        assertEquals(500, eventoSavado.getCapacidade());
    }

    @Test
    void criarIngresso_DeveSalvarIngresso_QuandoDadosValidos() {
        // Arrange
        Ingresso novoIngresso = new Ingresso();
        novoIngresso.setEvento(evento);
        novoIngresso.setModalidade("GERAL");
        novoIngresso.setPreco(50.0);
        novoIngresso.setQuantidade(30);
        novoIngresso.setQuantidadeDisponivel(30);

        // Act
        Ingresso ingressoSalvo = ingressoService.criarIngresso(novoIngresso);

        // Assert
        assertNotNull(ingressoSalvo.getId());
        assertEquals("GERAL", ingressoSalvo.getModalidade());
        assertEquals(50.0, ingressoSalvo.getPreco());
        assertEquals(30, ingressoSalvo.getQuantidade());
    }

    @Test
    void criarUsuario_DeveSalvarUsuario_QuandoDadosValidos() {
        // Arrange
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("Maria Oliveira");
        novoUsuario.setEmail("maria.oliveira@example.com");
        novoUsuario.setSenha("senha456");

        // Act
        Usuario usuarioSalvo = usuarioService.criarUsuario(novoUsuario);

        // Assert
        assertNotNull(usuarioSalvo.getId());
        assertEquals("Maria Oliveira", usuarioSalvo.getNome());
        assertEquals("maria.oliveira@example.com", usuarioSalvo.getEmail());
    }

    @Test
    void criarUsuario_DeveLancarExcecao_QuandoEmailJaExiste() {
        // Arrange
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("João Silva");
        novoUsuario.setEmail("joao.silva@example.com"); // Email já existe
        novoUsuario.setSenha("senha123");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> usuarioService.criarUsuario(novoUsuario));
    }
}