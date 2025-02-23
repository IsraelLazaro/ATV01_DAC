package br.edu.ifpb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@NoArgsConstructor
@Data
@Entity
@Table(name = "compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference("usuario-compras")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    @JsonBackReference("evento-compras") 
    private Evento evento;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private String modalidade;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private LocalDateTime dataCompra;
}