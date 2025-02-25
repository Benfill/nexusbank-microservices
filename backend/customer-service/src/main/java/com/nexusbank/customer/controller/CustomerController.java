package com.nexusbank.customer.controller;

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

import com.nexusbank.customer.dto.DtoRequest;
import com.nexusbank.customer.service.ICustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private ICustomerService service;

    @GetMapping
    public ResponseEntity<?> index(@RequestParam(defaultValue = "1", name = "page") Integer page,
	    @RequestParam(defaultValue = "3", name = "size") Integer size) {
	return ResponseEntity.ok(service.getAll(page, size));
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> search(@PathVariable Long id) {
	return ResponseEntity.ok(service.searchById(id));
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
}
