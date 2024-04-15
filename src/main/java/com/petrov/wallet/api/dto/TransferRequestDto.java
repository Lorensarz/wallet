package com.petrov.wallet.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import static com.petrov.wallet.util.OpenApiConstants.EXAMPLE_AMOUNT;
import static com.petrov.wallet.util.OpenApiConstants.EXAMPLE_UUID;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "dto for transfer money")
public class TransferRequestDto {

    @Schema(example = EXAMPLE_UUID, defaultValue = EXAMPLE_UUID)
    @JsonProperty("remitterWalletId")
    private String remitterWalletId;

    @Schema(example = EXAMPLE_UUID, defaultValue = EXAMPLE_UUID)
    @JsonProperty("payeeWalletId")
    private String payeeWalletId;

    @Schema(example = EXAMPLE_AMOUNT, defaultValue = EXAMPLE_AMOUNT)
    @JsonProperty("amount")
    private BigDecimal amount;
}
