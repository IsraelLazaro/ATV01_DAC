package br.edu.ifpb.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.*;


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
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @Transient
    private BigDecimal valorTotal;
}
