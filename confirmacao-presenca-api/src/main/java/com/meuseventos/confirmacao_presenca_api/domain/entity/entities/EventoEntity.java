package com.meuseventos.confirmacao_presenca_api.domain.entity.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "evento", schema = "confirmacao_presenca")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ev_id")
    private UUID id;

    @Column(name = "ev_titulo", length = 100, nullable = false)
    private String titulo;

    @Column(name = "ev_data_evento", nullable = false)
    private LocalDate dataEvento;

    @Column(name = "ev_codigo", length = 8, nullable = false)
    private String codigo;
}
