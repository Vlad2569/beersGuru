/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbv.web.spring6restmvs.dtos.CustomerDTO;
import com.jbv.web.spring6restmvs.services.CustomerService;
import com.jbv.web.spring6restmvs.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

    @Captor
    ArgumentCaptor<CustomerDTO> customerArgumentCaptor = ArgumentCaptor.forClass(CustomerDTO.class);

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testGetByIdNotFound() throws Exception {

        given(customerService.getCustomerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetCustomerById() throws Exception {

        CustomerDTO testCustomerDTO = customerServiceImpl.getCustomers().get(0);

        given(customerService.getCustomerById(testCustomerDTO.getCustomerId())).willReturn(Optional.of(testCustomerDTO));

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, testCustomerDTO.getCustomerId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId", is(testCustomerDTO.getCustomerId().toString())))
                .andExpect(jsonPath("$.name", is(testCustomerDTO.getName())));
    }

    @Test
    void testGetAllCustomers() throws Exception {

        given(customerService.getCustomers()).willReturn(customerServiceImpl.getCustomers());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void testCreateCustomer() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImpl.getCustomers().get(0);
        testCustomerDTO.setCustomerId(null);
        testCustomerDTO.setVersion(null);

        given(customerService.createCustomer(any(CustomerDTO.class))).willReturn(customerServiceImpl.getCustomers().get(1));

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateCustomerById() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImpl.getCustomers().get(0);

        given(customerService.updateCustomerById(any(), any())).willReturn(Optional.of(testCustomerDTO));

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, testCustomerDTO.getCustomerId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerDTO)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomerById(any(UUID.class), any(CustomerDTO.class));
    }

    @Test
    void testDeleteCustomerById() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImpl.getCustomers().get(0);

        given(customerService.deleteCustomerById(any())).willReturn(true);

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, testCustomerDTO.getCustomerId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());
        assertThat(testCustomerDTO.getCustomerId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testPatchCustomerById() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImpl.getCustomers().get(0);

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("name", "New Name");

        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, testCustomerDTO.getCustomerId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(),
                customerArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(testCustomerDTO.getCustomerId());
        assertThat(customerArgumentCaptor.getValue().getName())
                .isEqualTo(customerMap.get("name"));
    }
}