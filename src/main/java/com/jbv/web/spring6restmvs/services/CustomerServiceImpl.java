/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.services;

import com.jbv.web.spring6restmvs.models.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService{

    private final Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .customerId(UUID.randomUUID())
                .version(1)
                .name("Vasko Milev")
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .customerId(UUID.randomUUID())
                .version(1)
                .name("Slavka Dineva")
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .customerId(UUID.randomUUID())
                .version(1)
                .name("Rumen Genev")
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getCustomerId(), customer1);
        customerMap.put(customer2.getCustomerId(), customer2);
        customerMap.put(customer3.getCustomerId(), customer3);

    }

    @Override
    public Customer getCustomerById(UUID customerId) {

        log.debug("Get customer By Id in Customer service: " + customerId.toString());

        return customerMap.get(customerId);
    }

    @Override
    public List<Customer> getCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer createCustomer(Customer customer) {

        Customer newCustomer = Customer.builder()
                .customerId(UUID.randomUUID())
                .version(customer.getVersion())
                .name(customer.getName())
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .build();

        customerMap.put(newCustomer.getCustomerId(), newCustomer);

        return newCustomer;
    }

    @Override
    public void updateCustomerById(UUID customerId, Customer customer) {

        Customer customerToUpdate = customerMap.get(customerId);

        customerToUpdate.setVersion(customer.getVersion());
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setLastUpdate(LocalDateTime.now());
    }

    @Override
    public void deleteCustomerById(UUID customerId) {

        customerMap.remove(customerId);
    }

    @Override
    public void patchCustomerById(UUID customerId, Customer customer) {

        Customer customerToPatch = customerMap.get(customerId);

        if (customer.getVersion() != null) {
            customerToPatch.setVersion(customer.getVersion());
        }

        if (StringUtils.hasText(customer.getName())) {
            customerToPatch.setName(customer.getName());
        }

        customerToPatch.setLastUpdate(LocalDateTime.now());
    }
}