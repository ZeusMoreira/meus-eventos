package com.meuseventos.confirmacao_presenca_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ConfirmacaoPresencaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfirmacaoPresencaApiApplication.class, args);
	}

}
