package br.edu.ifpb.repository;

import br.edu.ifpb.model.Ingresso;
import br.edu.ifpb.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
       Optional<Ingresso> findByEventoId(Long eventoId);
       Optional<Ingresso> findByEventoAndModalidade(Evento evento, String modalidade);

       @Query("SELECT i FROM Ingresso i " +
              "WHERE (:eventoId IS NULL OR i.evento.id = :eventoId) " +
              "AND (:modalidade IS NULL OR i.modalidade = :modalidade)")
       List<Ingresso> findByEventoIdAndModalidade(
              @Param("eventoId") Long eventoId,
              @Param("modalidade") String modalidade);
}