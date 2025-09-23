CREATE SCHEMA IF NOT EXISTS confirmacao_presenca;

CREATE TABLE IF NOT EXISTS confirmacao_presenca.evento (
    ev_id UUID NOT NULL PRIMARY KEY,
    ev_codigo VARCHAR(8) UNIQUE NOT NULL,
    ev_titulo VARCHAR(100) NOT NULL,
    ev_data_evento TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS confirmacao_presenca.participante (
    pp_id UUID NOT NULL PRIMARY KEY,
    pp_nome VARCHAR(100) NOT NULL,
    pp_cpf VARCHAR(11) NOT NULL,
    ev_id UUID NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_evento
        FOREIGN KEY (ev_id)
        REFERENCES confirmacao_presenca.evento(ev_id)
        ON DELETE CASCADE,
    CONSTRAINT unq_cpf_evento UNIQUE (pp_cpf, ev_id)
);

CREATE INDEX idx_pp_nome ON confirmacao_presenca.participante(pp_nome);
CREATE INDEX idx_pp_cpf ON confirmacao_presenca.participante(pp_cpf);
CREATE INDEX idx_participante_evento ON confirmacao_presenca.participante(ev_id);

