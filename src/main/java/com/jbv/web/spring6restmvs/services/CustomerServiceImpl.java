/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.services;

import com.jbv.web.spring6restmvs.dtos.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService{

    private final Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .customerId(UUID.randomUUID())
                .version(1)
                .name("Vasko Milev")
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .build();

        CustomerDTO customerDTO2 = CustomerDTO.builder()
                .customerId(UUID.randomUUID())
                .version(1)
                .name("Slavka Dineva")
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .build();

        CustomerDTO customerDTO3 = CustomerDTO.builder()
                .customerId(UUID.randomUUID())
                .version(1)
                .name("Rumen Genev")
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .build();

        customerMap.put(customerDTO1.getCustomerId(), customerDTO1);
        customerMap.put(customerDTO2.getCustomerId(), customerDTO2);
        customerMap.put(customerDTO3.getCustomerId(), customerDTO3);

    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID customerId) {

        log.debug("Get customer By Id in Customer service: " + customerId.toString());

        return Optional.ofNullable(customerMap.get(customerId));
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {

        CustomerDTO newCustomerDTO = CustomerDTO.builder()
                .customerId(UUID.randomUUID())
                .version(customerDTO.getVersion())
                .name(customerDTO.getName())
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .build();

        customerMap.put(newCustomerDTO.getCustomerId(), newCustomerDTO);

        return newCustomerDTO;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customerDTO) {

        CustomerDTO customerDTOToUpdate = customerMap.get(customerId);

        customerDTOToUpdate.setVersion(customerDTO.getVersion());
        customerDTOToUpdate.setName(customerDTO.getName());
        customerDTOToUpdate.setLastUpdate(LocalDateTime.now());

        return Optional.of(customerDTOToUpdate);
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {

        customerMap.remove(customerId);
        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customerDTO) {

        CustomerDTO customerDTOToPatch = customerMap.get(customerId);

        if (customerDTO.getVersion() != null) {
            customerDTOToPatch.setVersion(customerDTO.getVersion());
        }

        if (StringUtils.hasText(customerDTO.getName())) {
            customerDTOToPatch.setName(customerDTO.getName());
        }

        customerDTOToPatch.setLastUpdate(LocalDateTime.now());
        return Optional.of(customerDTOToPatch);
    }
}