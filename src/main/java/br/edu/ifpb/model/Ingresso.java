package br.edu.ifpb.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@NoArgsConstructor
@Data
@Entity
@Table(name = "ingresso")
public class Ingresso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    @JsonBackReference("evento-ingressos")
    private Evento evento;

    @Column(nullable = false)
    private String modalidade;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private int quantidadeDisponivel;

    private String restricao;
}