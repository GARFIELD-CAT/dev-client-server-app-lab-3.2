package ru.yagunov.schedule_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignClientConfig {

    @Bean
    public ru.yagunov.data_service.api.UserEventApi dataServiceApi() {
        ru.yagunov.data_service.ApiClient apiClient = new ru.yagunov.data_service.ApiClient();
        apiClient.setBasePath("http://localhost:8083");
        return apiClient.buildClient(ru.yagunov.data_service.api.UserEventApi .class);
    }

    @Bean
    public ru.yagunov.report_service.api.UserEventApi reportServiceApi() {
        ru.yagunov.report_service.ApiClient apiClient = new ru.yagunov.report_service.ApiClient();
        apiClient.setBasePath("http://localhost:8080");
        return apiClient.buildClient(ru.yagunov.report_service.api.UserEventApi.class);
    }
}