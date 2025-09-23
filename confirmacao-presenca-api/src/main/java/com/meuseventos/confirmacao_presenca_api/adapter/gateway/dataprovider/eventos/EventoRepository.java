package com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.eventos;

import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.EventoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity, UUID> {
    Optional<EventoEntity> findByCodigo(String codigo);

    Page<EventoEntity> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
}
