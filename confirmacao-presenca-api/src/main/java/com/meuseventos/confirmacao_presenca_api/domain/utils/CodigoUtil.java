package com.meuseventos.confirmacao_presenca_api.domain.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class CodigoUtil {
    public static String gerarCodigo() {
        return RandomStringUtils.randomAlphanumeric(8); // ex: "aZ3xL9pQ"
    }
}

