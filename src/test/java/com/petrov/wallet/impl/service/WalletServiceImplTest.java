package com.petrov.wallet.impl.service;

import com.petrov.wallet.api.dto.SuccessResponseDto;
import com.petrov.wallet.api.dto.WalletCreateRequestDto;
import com.petrov.wallet.api.dto.WalletResponseDto;
import com.petrov.wallet.db.entities.Wallet;
import com.petrov.wallet.db.entities.enums.WalletType;
import com.petrov.wallet.db.repositories.WalletRepository;
import com.petrov.wallet.impl.exception.ValidationException;
import com.petrov.wallet.impl.mapper.WalletMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletMapper walletMapper;

    @InjectMocks
    private WalletServiceImpl walletService;

    private UUID uuid = UUID.randomUUID();

    private BigDecimal bigDecimal = BigDecimal.TEN;

    WalletType walletType = WalletType.PAYMENT;

    LocalDateTime timeStamp = LocalDateTime.of(2024, 1, 1, 1, 0);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Создание кошелька успешно завершается")
    void testCreate_Success() {
        WalletCreateRequestDto requestDto = new WalletCreateRequestDto();
        requestDto.setWalletType(WalletType.PAYMENT);

        Wallet wallet = new Wallet();
        SuccessResponseDto expectedResponse = new SuccessResponseDto(HttpStatus.CREATED, "Успешно!");

        when(walletMapper.toWallet(requestDto)).thenReturn(wallet);
        when(walletRepository.save(wallet)).thenReturn(wallet);

        SuccessResponseDto responseDto = walletService.create(requestDto);

        assertNotNull(responseDto);
        assertEquals(expectedResponse, responseDto);

        verify(walletMapper, times(1)).toWallet(requestDto);
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    @DisplayName("Исключение при попытке создать пустой кошелек")
    void testCreate_ValidationException() {
        WalletCreateRequestDto requestDto = new WalletCreateRequestDto();

        assertThrows(ValidationException.class, () -> walletService.create(requestDto));
    }

    @Test
    @DisplayName("Сохранение кошелька успешно завершается")
    void testSave_Success() {
        WalletResponseDto responseDto = new WalletResponseDto();
        responseDto.setId(uuid);

        Wallet wallet = new Wallet();
        SuccessResponseDto expectedResponse = new SuccessResponseDto(HttpStatus.CREATED, "Успешно!");

        when(walletMapper.toWallet(responseDto)).thenReturn(wallet);
        when(walletRepository.save(wallet)).thenReturn(wallet);

        SuccessResponseDto result = walletService.save(responseDto);

        assertNotNull(result);
        assertEquals(expectedResponse, result);

        verify(walletMapper, times(1)).toWallet(responseDto);
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    @DisplayName("Исключение при попытке сохранить пустой кошелек")
    void testSave_ValidationException() {
        assertThrows(ValidationException.class, () -> walletService.save(null));
    }

    @Test
    @DisplayName("Получение кошелька по ID успешно завершается")
    void testGetWalletById_Success() {
        Wallet wallet = new Wallet();
        wallet.setId(uuid);
        wallet.setBalance(bigDecimal);
        wallet.setWalletType(walletType);
        WalletResponseDto expectedResponse = new WalletResponseDto(uuid, bigDecimal, walletType);

        when(walletRepository.findById(uuid)).thenReturn(Optional.of(wallet));
        when(walletMapper.toWalletResponseDto(wallet)).thenReturn(expectedResponse);

        WalletResponseDto result = walletService.getWalletById(uuid);

        assertNotNull(result);
        assertEquals(expectedResponse, result);

        verify(walletRepository, times(1)).findById(uuid);
        verify(walletMapper, times(1)).toWalletResponseDto(wallet);
    }

    @Test
    @DisplayName("Исключение при попытке получить несуществующий кошелек по ID")
    void testGetWalletById_WalletNotFound() {
        UUID walletId = uuid;

        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> walletService.getWalletById(walletId));

    }

    @Test
    @DisplayName("Вычитание суммы из баланса при валидных данных успешно завершается")
    void testSubtractBalance_Success() {
        BigDecimal transferAmount = BigDecimal.valueOf(100);
        String remitterWalletId = uuid.toString();

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(200));

        when(walletRepository.findById(any(UUID.class))).thenReturn(Optional.of(wallet));
        when(walletRepository.save(any())).thenReturn(wallet);

        Wallet result = walletService.subtractBalance(transferAmount, remitterWalletId);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(100), result.getBalance());

        verify(walletRepository, times(1)).save(any());
    }


    @Test
    @DisplayName("Исключение при попытке вычитания суммы из баланса, превышающей баланс")
    void testSubtractBalance_InsufficientBalance() {
        BigDecimal transferAmount = BigDecimal.valueOf(30);
        String remitterWalletId = uuid.toString();
        Wallet wallet = new Wallet();
        wallet.setBalance(bigDecimal);

        when(walletRepository.findById(any(UUID.class))).thenReturn(Optional.of(wallet));

        assertThrows(ValidationException.class, () -> walletService.subtractBalance(transferAmount, remitterWalletId));
    }

    @Test
    @DisplayName("Прибавление суммы на баланс при валидных данных успешно завершается")
    void testAddBalance_Success() {
        BigDecimal transferAmount = BigDecimal.valueOf(100);
        String payeeWalletId = uuid.toString();
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(100));

        when(walletRepository.findById(any(UUID.class))).thenReturn(Optional.of(wallet));
        when(walletRepository.save(any())).thenReturn(wallet);

        Wallet result = walletService.addBalance(transferAmount, payeeWalletId);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(200), result.getBalance());

        verify(walletRepository, times(1)).save(any());
    }

}
