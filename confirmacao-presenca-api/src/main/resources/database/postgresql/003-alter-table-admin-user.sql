ALTER TABLE confirmacao_presenca.admin_user
    ADD COLUMN reset_token VARCHAR(255),
    ADD COLUMN reset_expiracao TIMESTAMP;