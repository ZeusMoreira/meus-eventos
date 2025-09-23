package com.meuseventos.confirmacao_presenca_api.domain.service.evento.criar;

import com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.eventos.EventoRepository;
import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.CriarEventoInputDTO;
import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.EventoDTO;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.EventoEntity;
import com.meuseventos.confirmacao_presenca_api.domain.utils.CodigoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarEventoService {

    private final EventoRepository repository;

    public EventoEntity salvar(CriarEventoInputDTO dto) {
        EventoEntity evento = EventoEntity.builder()
                .titulo(dto.getTitulo())
                .dataEvento(dto.getDataEvento())
                .codigo(CodigoUtil.gerarCodigo())
                .build();

        return repository.save(evento);
    }
}
