package com.secondprojinitiumback.common.bank.Repository;

import com.secondprojinitiumback.common.bank.domain.BankAccount;
import com.secondprojinitiumback.common.domain.CommonCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    Optional<BankAccount> findByAccountNo(String accountNo);
    Optional<BankAccount> findFirstByOwnerIdOrderByAccountNoDesc(String ownerId);

}
