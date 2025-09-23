package com.meuseventos.confirmacao_presenca_api.domain.entity.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "participante", schema = "confirmacao_presenca")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ParticipanteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pp_id")
    private UUID id;

    @Column(name = "pp_nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "pp_cpf", length = 11, nullable = false)
    private String cpf;

    @CreatedDate
    @Column(name = "criado_em")
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ev_id", nullable = false)
    private EventoEntity evento;

}
