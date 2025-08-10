package com.secondprojinitiumback.common.bank.Repository;

import com.secondprojinitiumback.common.bank.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    Optional<BankAccount> findByAccountNo(String accountNo);
    Optional<BankAccount> findByOwnerId(String ownerId);
}
