package com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.participantes;

import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.EventoEntity;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.ParticipanteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParticipanteRepository extends JpaRepository<ParticipanteEntity, UUID> {
    Optional<ParticipanteEntity> findByCpfAndEventoId(String cpf, UUID id);
    List<ParticipanteEntity> findAllByEventoId(UUID id);
    @Query("SELECT p.evento.id, COUNT(p) " +
            "FROM ParticipanteEntity p " +
            "WHERE p.evento.id IN :ids " +
            "GROUP BY p.evento.id")
    List<Object[]> contarParticipantesPorEventoIds(@Param("ids") List<UUID> ids);

    Page<ParticipanteEntity> findByNomeContainingIgnoreCaseOrCpfContainingIgnoreCase(
            String nome, String cpf, Pageable pageable
    );

    boolean existsByIdAndEventoId(UUID id, UUID idEvento);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM ParticipanteEntity p " +
            "WHERE p.id = :participanteId AND p.evento.id = :eventoId")
    void deleteByIdAndEventoId(@Param("participanteId") UUID participanteId,
                              @Param("eventoId") UUID eventoId);

    @Query("""
    select p
    from ParticipanteEntity p
    where p.evento.id = :eventoId
  """)
    Page<ParticipanteEntity> findByEventoId(@Param("eventoId") UUID eventoId, Pageable pageable);

    @Query("""
    select p
    from ParticipanteEntity p
    where p.evento.id = :eventoId
      and (
        lower(p.nome) like lower(concat('%', :filtro, '%'))
        or p.cpf like concat('%', :filtro, '%')
      )
  """)
    Page<ParticipanteEntity> searchByEventoAndFiltro(@Param("eventoId") UUID eventoId,
                                                     @Param("filtro") String filtro,
                                                     Pageable pageable);
}
