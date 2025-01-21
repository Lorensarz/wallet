package com.petrov.wallet.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@Schema(description = "Dto for response of success changes wallet")
public class SuccessResponseDto {

    private Integer code;
    private String message;

    public SuccessResponseDto(HttpStatus status, String message) {
        this.code = status.value();
        this.message = message;
    }
}
