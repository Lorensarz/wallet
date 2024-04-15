package com.petrov.wallet.db.repositories;

import com.petrov.wallet.db.entities.Wallet;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * WalletRepository.
 */
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

}
