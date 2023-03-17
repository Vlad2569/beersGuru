/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.mappers;

import com.jbv.web.spring6restmvs.dtos.CustomerDTO;
import com.jbv.web.spring6restmvs.models.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDto(Customer customer);
}