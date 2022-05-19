package org.sid.ebancbackend;

import org.sid.ebancbackend.entities.*;
import org.sid.ebancbackend.enums.AccountStatus;
import org.sid.ebancbackend.enums.OperationType;
import org.sid.ebancbackend.repositories.AccountOperationRepository;
import org.sid.ebancbackend.repositories.BankAccountRepository;
import org.sid.ebancbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class    EbancBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbancBackendApplication.class, args);
    }

@Bean
@Transactional
    CommandLineRunner commandLineRunner(BankAccountRepository bankAccountRepository){
return args -> {

    BankAccount bankAccount = bankAccountRepository.findById("119a36d0-a121-4c9e-b7e5-246f78e246ba").orElse(null);
    if (bankAccount!=null) {
        System.out.println("************");
        System.out.println(bankAccount.getId());
        System.out.println(bankAccount.getBalance());
        System.out.println(bankAccount.getStatus());
        System.out.println(bankAccount.getCreatedAt());
        System.out.println(bankAccount.getCustomer().getName());
        System.out.println(bankAccount.getClass().getSimpleName());
        if (bankAccount instanceof CurrentAccount) {
            System.out.println("Over Draft=> " + ((CurrentAccount) bankAccount).getOverDraft());
        } else if (bankAccount instanceof SavingAccount) {
            System.out.println("Rate" + ((SavingAccount) bankAccount).getInterestRate());
        }
        bankAccount.getAccountOperations().forEach(op -> {

            System.out.println(op.getType() + "\t" + op.getAmount() + "\t" + op.getOperationDate());

        });
    }
};
    }

    //@Bean
    CommandLineRunner Start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {
        return args ->{
            Stream.of("MOUNIR","MOHAMMED","ACHRAF").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);

            });
            customerRepository.findAll().forEach(cust-> {
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*9000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);

                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*9000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);

                bankAccountRepository.save(savingAccount);

            });
            bankAccountRepository.findAll().forEach(acc->{
                for (int i=0;i<5;i++){

                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*11000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);

                }



            });

        };
    }

}
