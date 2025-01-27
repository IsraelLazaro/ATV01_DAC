package br.edu.ifpb.model;

import java.util.*;

import jakarta.persistence.*;
import lombok. *;


@NoArgsConstructor
@Data
@Entity
@Table(name = "usuario")
public class Usuario {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Compra> compras = new ArrayList<>();
}
