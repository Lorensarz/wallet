package com.petrov.wallet.api.service;

import com.petrov.wallet.api.dto.SuccessResponseDto;
import com.petrov.wallet.api.dto.WalletCreateRequestDto;
import com.petrov.wallet.api.dto.WalletResponseDto;
import com.petrov.wallet.db.entities.Wallet;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * WalletService interface.
 */
public interface WalletService {

    SuccessResponseDto create(WalletCreateRequestDto walletCreateRequestDto);

    SuccessResponseDto save(WalletResponseDto walletResponseDto);

    WalletResponseDto getWalletById(UUID walletId);

    Wallet subtractBalance(BigDecimal transferAmount, String remitterWalletId);

    Wallet addBalance(BigDecimal transferAmount, String payeeWalletId);
}
