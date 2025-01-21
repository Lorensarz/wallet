package com.petrov.wallet.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.petrov.wallet.db.entities.enums.WalletType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.petrov.wallet.util.OpenApiConstants.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO for new wallet creation")
public class WalletCreateRequestDto {

    @Schema(description = DESCRIPTION_WALLET_TYPE, defaultValue = EXAMPLE_WALLET_TYPE)
    @NotNull
    @JsonProperty("walletType")
    private WalletType walletType;

    @Schema(description = DESCRIPTION_AMOUNT, defaultValue = EXAMPLE_AMOUNT)
    @NotNull
    @JsonProperty("amount")
    private BigDecimal balance;

}
