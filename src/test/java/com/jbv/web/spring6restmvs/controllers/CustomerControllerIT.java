/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.controllers;

import com.jbv.web.spring6restmvs.dtos.CustomerDTO;
import com.jbv.web.spring6restmvs.exceptions.NotFoundException;
import com.jbv.web.spring6restmvs.mappers.CustomerMapper;
import com.jbv.web.spring6restmvs.models.Customer;
import com.jbv.web.spring6restmvs.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testGetCustomers() {

        List<CustomerDTO> customerDTOS = customerController.getCustomers();

        assertThat(customerDTOS.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {

        customerRepository.deleteAll();

        List<CustomerDTO> customerDTOS = customerController.getCustomers();

        assertThat(customerDTOS.size()).isEqualTo(0);
    }

    @Test
    void testGetById() {

        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerDTO = customerController.getCustomerById(customer.getCustomerId());

        assertThat(customerDTO).isNotNull();
    }

    @Test
    void testNotFound() {

        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testCreateCustomer() {

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("New Name")
                .build();

        ResponseEntity responseEntity = customerController.createCustomer(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateCustomer() {

        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setCustomerId(null);
        customerDTO.setVersion(null);
        final String customerName = "UPDATED";
        customerDTO.setName(customerName);

        ResponseEntity responseEntity = customerController.updateCustomerById(customer.getCustomerId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getCustomerId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(customerName);
    }

    @Test
    void testUpdateNotFound() {

        assertThrows(NotFoundException.class, () -> {
            customerController.updateCustomerById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteCustomer() {

        Customer customer = customerRepository.findAll().get(0);

        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getCustomerId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(customerRepository.findById(customer.getCustomerId())).isEmpty();
    }

    @Test
    void testDeleteNotFound() {

        assertThrows(NotFoundException.class, () -> {
            customerController.deleteCustomerById(UUID.randomUUID());
        });
    }
}