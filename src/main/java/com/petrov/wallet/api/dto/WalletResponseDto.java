package com.petrov.wallet.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.petrov.wallet.db.entities.enums.WalletType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

import static com.petrov.wallet.util.OpenApiConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "dto for wallets")
public class WalletResponseDto {
    @Schema(example = EXAMPLE_UUID, defaultValue = EXAMPLE_UUID)
    @JsonProperty("id")
    private UUID id;

    @Schema(example = EXAMPLE_BALANCE_START, defaultValue = EXAMPLE_BALANCE_START)
    @JsonProperty("balance")
    private BigDecimal balance;

    @Schema(example = EXAMPLE_WALLET_TYPE, defaultValue = EXAMPLE_WALLET_TYPE)
    @JsonProperty("walletType")
    private WalletType walletType;

}
