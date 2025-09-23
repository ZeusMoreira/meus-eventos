package com.meuseventos.confirmacao_presenca_api.domain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginacaoDTO<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int numberOfElements;
}
