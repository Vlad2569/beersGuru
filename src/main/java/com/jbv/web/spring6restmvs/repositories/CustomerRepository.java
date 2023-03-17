/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.repositories;

import com.jbv.web.spring6restmvs.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}