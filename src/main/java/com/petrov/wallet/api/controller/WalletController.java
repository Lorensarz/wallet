package com.petrov.wallet.api.controller;

import com.petrov.wallet.api.dto.SuccessResponseDto;
import com.petrov.wallet.api.dto.TransferRequestDto;
import com.petrov.wallet.api.dto.WalletCreateRequestDto;
import com.petrov.wallet.api.dto.WalletResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Wallet controller", description = "Endpoints to work with Wallet entity.")
@RequestMapping(value = "/api/v1/wallets")
public interface WalletController {

    @Operation(summary = "Create wallet", description = "create wallet by params from dto object")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    SuccessResponseDto create(@Parameter(description = "wallet for creation", required = true)
                              @RequestBody @Valid WalletCreateRequestDto walletCreateRequestDto);

    @Operation(summary = "Get wallet", description = "get wallet by id")
    @GetMapping("/{walletId}")
    WalletResponseDto getWallet(@Parameter(description = "wallet ID", required = true)
                                @PathVariable UUID walletId);

    @Operation(summary = "Transfer money", description = "transfer money by params from dto object")
    @PostMapping("/transfer")
    SuccessResponseDto transferMoney(@Valid @RequestBody TransferRequestDto transferRequestDto);

}
