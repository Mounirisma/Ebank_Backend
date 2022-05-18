package org.sid.ebancbackend.repositories;

import org.sid.ebancbackend.entities.BankAccount;
import org.sid.ebancbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository <BankAccount,String>{

}
