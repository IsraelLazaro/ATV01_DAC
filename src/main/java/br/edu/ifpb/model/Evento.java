package br.edu.ifpb.model;

import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false)
    private String local;

    @Column(nullable = false)
    private int capacidade;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<Ingresso> ingressos = new ArrayList<>();

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<Compra> compras = new ArrayList<>();
}
