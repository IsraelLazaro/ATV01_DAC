package br.edu.ifpb.repository;

import br.edu.ifpb.model.Compra;
import br.edu.ifpb.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
       List<Compra> findByUsuario_Id(Long usuarioId);
       List<Compra> findByEvento_Id(Long eventoId);
       List<Compra> findByModalidade(String modalidade);
       List<Compra> findByUsuario_IdAndModalidade(Long usuarioId, String modalidade);

       @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
              "FROM Evento e " +
              "WHERE e.dataInicio = :dataInicio AND e.horario = :horario AND e.local = :local")
       boolean existsByEvento_DataInicioAndEvento_HorarioAndEvento_Local(
              @Param("dataInicio") LocalDate dataInicio,
              @Param("horario") LocalTime horario,
              @Param("local") String local);

       @Query("SELECT e FROM Evento e " +
              "WHERE (:categoria IS NULL OR e.categoria = :categoria) " +
              "AND (:data IS NULL OR e.dataInicio = :data) " +
              "AND (:local IS NULL OR e.local = :local)")
       List<Evento> findByEvento_CategoriaAndEvento_DataInicioAndEvento_Local(
              @Param("categoria") String categoria,
              @Param("data") LocalDate data,
              @Param("local") String local);

       @Query("SELECT e FROM Evento e " +
              "WHERE (:categoria IS NULL OR e.categoria = :categoria) " +
              "AND (:data IS NULL OR e.dataInicio = :data)")
       List<Evento> findByEvento_CategoriaAndEvento_DataInicio(
              @Param("categoria") String categoria,
              @Param("data") LocalDate data);

       @Query("SELECT e FROM Evento e " +
              "WHERE (:categoria IS NULL OR e.categoria = :categoria) " +
              "AND (:local IS NULL OR e.local = :local)")
       List<Evento> findByEvento_CategoriaAndEvento_Local(
              @Param("categoria") String categoria,
              @Param("local") String local);

       @Query("SELECT e FROM Evento e " +
              "WHERE (:data IS NULL OR e.dataInicio = :data) " +
              "AND (:local IS NULL OR e.local = :local)")
       List<Evento> findByEvento_DataInicioAndEvento_Local(
              @Param("data") LocalDate data,
              @Param("local") String local);

       @Query("SELECT e FROM Evento e " +
              "WHERE (:categoria IS NULL OR e.categoria = :categoria)")
       List<Evento> findByEvento_Categoria(@Param("categoria") String categoria);

       @Query("SELECT e FROM Evento e " +
              "WHERE (:data IS NULL OR e.dataInicio = :data)")
       List<Evento> findByEvento_DataInicio(@Param("data") LocalDate data);

       @Query("SELECT e FROM Evento e " +
              "WHERE (:local IS NULL OR e.local = :local)")
       List<Evento> findByEvento_Local(@Param("local") String local);

       @Query("SELECT e FROM Evento e")
       List<Evento> findAllEventos();
}