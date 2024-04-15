package com.petrov.wallet.impl.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * Config for OpenApi.
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "Wallet Service",
        description = "This is the API for the Wallet Service", version = "v1"))
public class OpenApiConfig {

}
