package br.edu.ifpb.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Ingresso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @Column(nullable = false)
    private String modalidade;

    @Column(nullable = false)
    private BigDecimal preco;

    private int quantidade;
    
    private String restricao;
    
}