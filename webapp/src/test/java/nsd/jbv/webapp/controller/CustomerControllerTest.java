package nsd.jbv.webapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import nsd.jbv.webapp.model.Customer;
import nsd.jbv.webapp.service.CustomerService;
import nsd.jbv.webapp.service.CustomerServiceImpl;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testListCustomers() throws Exception {

        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void testGetCustomerById() throws Exception {

        Customer testCustomer = customerServiceImpl.listCustomers().get(0);

        given(customerService.getCustomerById(testCustomer.getId())).willReturn(testCustomer);

        mockMvc.perform(get(CustomerController.CUSTOMER_ID_PATH, testCustomer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
                .andExpect(jsonPath("$.name", is(testCustomer.getName())));

    }

    @Test
    void testHandlePost() throws Exception {

        Customer customer = customerServiceImpl.listCustomers().get(0);
        customer.setId(null);
        customer.setVersion(null);

        given(customerService.saveCustomer(any(Customer.class))).willReturn(customerServiceImpl.listCustomers().get(1));

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testHandlePut() throws Exception {

        Customer customer = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(put(CustomerController.CUSTOMER_ID_PATH, customer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk());

        verify(customerService).updateCustomerById(any(UUID.class), any(Customer.class));
    }

    @Test
    void testHandleDelete() throws Exception {

        Customer customer = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(delete(CustomerController.CUSTOMER_ID_PATH, customer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<UUID> uuid = ArgumentCaptor.forClass(UUID.class);
        verify(customerService).deleteCustomerById(uuid.capture());

        assertThat(customer.getId()).isEqualTo(uuid.getValue());
    }

}
