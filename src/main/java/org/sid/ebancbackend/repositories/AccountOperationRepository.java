package org.sid.ebancbackend.repositories;

import org.sid.ebancbackend.entities.AccountOperation;
import org.sid.ebancbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository <AccountOperation,Long>{


}
