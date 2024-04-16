package com.petrov.wallet.impl.service;

import com.petrov.wallet.api.dto.SuccessResponseDto;
import com.petrov.wallet.api.dto.WalletCreateRequestDto;
import com.petrov.wallet.api.dto.WalletResponseDto;
import com.petrov.wallet.api.service.WalletService;
import com.petrov.wallet.db.entities.Wallet;
import com.petrov.wallet.db.repositories.WalletRepository;
import com.petrov.wallet.impl.exception.ValidationException;
import com.petrov.wallet.impl.mapper.WalletMapper;
import jakarta.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    private static final String NOT_FOUND_MESSAGE = "Wallet with ID - %s is not found";

    @Override
    public SuccessResponseDto create(WalletCreateRequestDto walletCreateRequestDto) {
        if (walletCreateRequestDto.getWalletType() == null) {
            throw new ValidationException("Укажите тип кошелька");
        }

        Wallet wallet = walletMapper.toWallet(walletCreateRequestDto);

        walletRepository.save(wallet);

        return new SuccessResponseDto(HttpStatus.CREATED, "Успешно!");
    }

    @Override
    public SuccessResponseDto save(WalletResponseDto walletResponseDto) {
        if (walletResponseDto == null) {
            throw new ValidationException("Объект кошелька не должен быть пустым!");
        }

        Wallet wallet = walletMapper.toWallet(walletResponseDto);

        walletRepository.save(wallet);

        return new SuccessResponseDto(HttpStatus.CREATED, "Успешно!");
    }

    @Override
    public WalletResponseDto getWalletById(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalStateException(NOT_FOUND_MESSAGE));
        return walletMapper.toWalletResponseDto(wallet);
    }

    @Override
    public Wallet subtractBalance(BigDecimal transferAmount, String remitterWalletId) {
        return changeAccountBalanceOrThrow(transferAmount.negate(), remitterWalletId);
    }

    @Override
    public Wallet addBalance(BigDecimal transferAmount, String payeeWalletId) {
        return changeAccountBalanceOrThrow(transferAmount, payeeWalletId);

    }

    /**
     * This method find wallet by walletId or throws exception if account is not found and save change balance.
     *
     * @param delta    balance change delta positive or negative
     * @param walletId wallet's id
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    private Wallet changeAccountBalanceOrThrow(BigDecimal delta, String walletId) {
        Wallet wallet = walletRepository.findById(UUID.fromString(walletId))
                .orElseThrow(() -> new ValidationException(String.format(NOT_FOUND_MESSAGE, walletId)));

        BigDecimal newBalance = wallet.getBalance().add(delta);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Отрицательный баланс, перевод невозможен");
        }

        wallet.setBalance(newBalance);

        return walletRepository.save(wallet);
    }
}
