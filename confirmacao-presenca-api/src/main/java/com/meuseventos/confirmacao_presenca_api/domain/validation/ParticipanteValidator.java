package com.meuseventos.confirmacao_presenca_api.domain.validation;

import com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.participantes.ParticipanteRepository;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.EventoEntity;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.ParticipanteEntity;
import com.meuseventos.confirmacao_presenca_api.domain.exception.ConflitoException;
import com.meuseventos.confirmacao_presenca_api.domain.exception.CpfInvalidoException;
import com.meuseventos.confirmacao_presenca_api.domain.exception.NaoEncontradoException;
import com.meuseventos.confirmacao_presenca_api.domain.exception.UuidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ParticipanteValidator {

    private final ParticipanteRepository repository;

    public UUID validarUuid(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new UuidException("O id informado não é um UUID válido!");
        }
    }

    public UUID validarExistenciaParticipanteNoEvento(UUID idEvento, String id) {
        UUID idParticipante = validarUuid(id);
        if(!ehParticipanteValido(idParticipante, idEvento)) {
            throw new NaoEncontradoException("Participante não está presente neste evento.");
        }
        return idParticipante;
    }

    public void validar(String cpf, EventoEntity evento) {
        validarCpf(cpf);
        Optional<ParticipanteEntity> participante = repository.findByCpfAndEventoId(cpf, evento.getId());

        if (participante.isPresent()) {
            throw new ConflitoException("Participante já marcou presença no evento.");
        }
    }

    public boolean ehParticipanteValido(UUID id, UUID idEvento) {
        return repository.existsByIdAndEventoId(id, idEvento);
    }

    private void validarCpf(String cpf) {
        if (!cpf.matches("\\d{11}")) {
            throw new CpfInvalidoException("CPF deve conter exatamente 11 dígitos numéricos");
        }

        if (cpf.chars().distinct().count() == 1) {
            throw new CpfInvalidoException("CPF inválido: sequência repetida");
        }

        if (!ehCpfValido(cpf)) {
            throw new CpfInvalidoException("CPF inválido");
        }
    }

    private boolean ehCpfValido(String cpf) {
        try {
            int soma = 0, resto;

            for (int i = 1; i <= 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i - 1)) * (11 - i);
            }
            resto = (soma * 10) % 11;
            if (resto == 10) resto = 0;
            if (resto != Character.getNumericValue(cpf.charAt(9))) return false;

            soma = 0;
            for (int i = 1; i <= 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i - 1)) * (12 - i);
            }
            resto = (soma * 10) % 11;
            if (resto == 10) resto = 0;
            return resto == Character.getNumericValue(cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }
}
