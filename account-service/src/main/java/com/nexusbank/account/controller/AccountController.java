package com.nexusbank.account.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexusbank.account.dto.DtoRequest;
import com.nexusbank.account.repository.AccountRepository;
import com.nexusbank.account.service.IAccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private IAccountService service;

    @Autowired
    private AccountRepository repository;

    @GetMapping
    public ResponseEntity<?> index(@RequestParam(defaultValue = "1", name = "page") Integer page,
	    @RequestParam(defaultValue = "3", name = "size") Integer size) {
	return ResponseEntity.ok(service.getAll(page, size));
    }

    @GetMapping("/display/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
	return ResponseEntity.ok(service.display(id));
    }

    @PostMapping
    public ResponseEntity<?> store(@RequestBody @Valid DtoRequest dto) {
	return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid DtoRequest dto) {
	return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> store(@PathVariable Long id) {
	return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomerAccounts(@PathVariable Long id) {
	return ResponseEntity.ok(repository.findByCustomerId(id));
    }
}
