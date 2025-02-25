package com.nexusbank.account.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexusbank.account.entity.Account;
import com.nexusbank.account.entity.AccountType;
import com.nexusbank.account.repository.AccountRepository;

@Component
public class DoubleAccountValidator {

    @Autowired
    private AccountRepository repository;

    public boolean hasDuplicateAccount(Long customerId, AccountType accountType) {
	List<Account> accounts = repository.findByCustomerId(customerId);
	return accounts.stream().anyMatch(account -> account.getType() == accountType);
    }
}