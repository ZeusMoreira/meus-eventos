CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS confirmacao_presenca.admin_user (
    us_id UUID NOT NULL PRIMARY KEY,
    us_email VARCHAR(100) NOT NULL,
    us_senha VARCHAR(70) NOT NULL
);

INSERT INTO confirmacao_presenca.admin_user (us_id, us_email, us_senha)
SELECT gen_random_uuid(), 'zeus.moreira111@gmail.com', '$2a$10$Npxm9U./MiqydP7ySPx2b.WCRlGj79N/oYeO6uiSuZO/NNG1V93v2'
WHERE NOT EXISTS (SELECT 1 FROM confirmacao_presenca.admin_user WHERE us_email = 'zeus.moreira111@gmail.com');
