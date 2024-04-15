package com.petrov.wallet.impl.mapper;

import com.petrov.wallet.api.dto.WalletCreateRequestDto;
import com.petrov.wallet.api.dto.WalletResponseDto;
import com.petrov.wallet.db.entities.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WalletMapper {

    Wallet toWallet(WalletCreateRequestDto walletCreateRequestDto);

    Wallet toWallet(WalletResponseDto walletResponseDto);

    WalletResponseDto toWalletResponseDto(Wallet wallet);

}
