package com.petrov.wallet.impl.controller;

import com.petrov.wallet.api.controller.WalletController;
import com.petrov.wallet.api.dto.SuccessResponseDto;
import com.petrov.wallet.api.dto.TransferRequestDto;
import com.petrov.wallet.api.dto.WalletCreateRequestDto;
import com.petrov.wallet.api.dto.WalletResponseDto;
import com.petrov.wallet.api.service.WalletService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WalletControllerImpl implements WalletController {

    private final WalletService walletService;

    @Override
    public SuccessResponseDto create(WalletCreateRequestDto walletCreateRequestDto) {
        log.debug("Запрос на создание кошелька");

        SuccessResponseDto responseDto = walletService.create(walletCreateRequestDto);
        log.debug("Return status Accept");

        return responseDto;
    }

    @Override
    public WalletResponseDto getWallet(UUID walletId) {
        log.debug("Получение запроса на просмотр информации о кошельке с id: {}", walletId);

        WalletResponseDto responseDto = walletService.getWalletById(walletId);
        log.debug("Возврат запрошенного кошелька: {}", responseDto);

        return responseDto;
    }

    @Override
    @Transactional
    public SuccessResponseDto transferMoney(@RequestBody TransferRequestDto transferRequestDto) {
        log.debug("Запрос на перевод денег");

        try {
            log.debug("Вычитание суммы средств со счета отправителя");
            walletService.subtractBalance(transferRequestDto.getAmount(), transferRequestDto.getRemitterWalletId());

            log.debug("Добавление суммы средств на счет получателя");
            walletService.addBalance(transferRequestDto.getAmount(), transferRequestDto.getPayeeWalletId());

            log.debug("Транзакция успешно завершена");
            return new SuccessResponseDto(HttpStatus.OK, "Транзакция успешно завершена");
        } catch (Exception e) {
            log.error("Ошибка при выполнении транзакции: {}", e.getMessage());
            throw e;
        }
    }


}
