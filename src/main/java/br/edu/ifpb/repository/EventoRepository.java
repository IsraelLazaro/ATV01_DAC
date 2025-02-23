package br.edu.ifpb.repository;

import br.edu.ifpb.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
       @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
              "FROM Evento e " +
              "WHERE e.dataInicio = :dataInicio AND e.horario = :horario AND e.local = :local")
       boolean existsByDataInicioAndHorarioAndLocal(
              @Param("dataInicio") LocalDate dataInicio,
              @Param("horario") LocalTime horario,
              @Param("local") String local);

       @Query("SELECT e FROM Evento e " +
              "WHERE e.horario = :horario AND e.local = :local")
       List<Evento> findByLocalAndHorario(
              @Param("horario") LocalTime horario,
              @Param("local") String local);

       @Query("SELECT e FROM Evento e " +
              "WHERE (:data IS NULL OR e.dataInicio = :data) " +
              "AND (:local IS NULL OR e.local = :local)")
       List<Evento> findByDataInicioAndLocal(
              @Param("data") LocalDate data,
              @Param("local") String local);

       @Query("SELECT e FROM Evento e " +
              "WHERE (:categoria IS NULL OR e.categoria = :categoria) " +
              "AND (:data IS NULL OR e.dataInicio = :data) " +
              "AND (:local IS NULL OR e.local = :local)")
       List<Evento> findByCategoriaAndDataInicioAndLocal(
              @Param("categoria") String categoria,
              @Param("data") LocalDate data,
              @Param("local") String local);

       @Query("SELECT e FROM Evento e " +
              "WHERE (:categoria IS NULL OR e.categoria = :categoria) " +
              "AND (:data IS NULL OR e.dataInicio = :data)")
       List<Evento> findByCategoriaAndDataInicio(
              @Param("categoria") String categoria,
              @Param("data") LocalDate data);

       @Query("SELECT e FROM Evento e " +
              "WHERE (:categoria IS NULL OR e.categoria = :categoria) " +
              "AND (:local IS NULL OR e.local = :local)")
       List<Evento> findByCategoriaAndLocal(
              @Param("categoria") String categoria,
              @Param("local") String local);

       @Query("SELECT e FROM Evento e " +
              "WHERE (:categoria IS NULL OR e.categoria = :categoria)")
       List<Evento> findByCategoria(@Param("categoria") String categoria);

       @Query("SELECT e FROM Evento e " +
              "WHERE (:data IS NULL OR e.dataInicio = :data)")
       List<Evento> findByDataInicio(@Param("data") LocalDate data);

       @Query("SELECT e FROM Evento e " +
              "WHERE (:local IS NULL OR e.local = :local)")
       List<Evento> findByLocal(@Param("local") String local);

       @Query("SELECT e FROM Evento e")
       List<Evento> findAllEventos();
}