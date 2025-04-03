package com.testelemontech.solicitacoes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.lemontech.selfbooking.wsselfbooking.services.WsSelfBookingService;

@Configuration
public class WsConfig {

    @Bean
    public WsSelfBookingService wsSelfBookingService() {
        return new WsSelfBookingService();
    }
}
