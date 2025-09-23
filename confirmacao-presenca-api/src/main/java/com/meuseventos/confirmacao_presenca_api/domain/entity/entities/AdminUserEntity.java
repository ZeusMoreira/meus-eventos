package com.meuseventos.confirmacao_presenca_api.domain.entity.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "admin_user", schema = "confirmacao_presenca")
@Getter
@Setter
public class AdminUserEntity {

    @Id
    @Column(name = "us_id", nullable = false)
    private UUID id;

    @Column(name = "us_email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "us_senha", nullable = false, length = 100)
    private String senha;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "reset_expiracao")
    private LocalDateTime resetExpiracao;
}
