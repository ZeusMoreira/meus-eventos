package com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.adminUser;

import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.AdminUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUserEntity, UUID> {
    Optional<AdminUserEntity> findByEmail(String email);
    Optional<AdminUserEntity> findByResetToken(String token);
}
