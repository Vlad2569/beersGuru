/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.services;

import com.jbv.web.spring6restmvs.models.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    Customer getCustomerById(UUID customerId);

    List<Customer> getCustomers();

    Customer createCustomer(Customer customer);

    void updateCustomerById(UUID customerId, Customer customer);

    void deleteCustomerById(UUID customerId);

    void patchCustomerById(UUID customerId, Customer customer);

}