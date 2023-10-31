package nsd.jbv.webapp.controller;

import nsd.jbv.webapp.entity.Customer;
import nsd.jbv.webapp.exception.NotFoundException;
import nsd.jbv.webapp.mappers.CustomerMapper;
import nsd.jbv.webapp.model.CustomerDTO;
import nsd.jbv.webapp.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
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
    void testDeleteNotFound() {
        Class<NotFoundException> expectedType = NotFoundException.class;
        assertThrows(expectedType, () -> {
            customerController.handleDelete(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testHandleDelete() {
        Customer customer = customerRepository.findAll().get(0);

        ResponseEntity<String> responseEntity = customerController.handleDelete(customer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

    @Test
    void testUpdateNotFound() {
        Class<NotFoundException> expectedType = NotFoundException.class;
        assertThrows(expectedType, () -> {
            customerController.handlePut(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testHandlePut() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        final String customerName = "UPDATED";
        customerDTO.setName(customerName);

        ResponseEntity<String> responseEntity = customerController.handlePut(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(customerName);
    }

    @Rollback
    @Transactional
    @Test
    void testHandlePost() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("NewName")
                .build();

        ResponseEntity<CustomerDTO> responseEntity = customerController.handlePost(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }

    @Test
    void testCustomerIdNotFound() {
        UUID id = UUID.randomUUID();

        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(id));
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());

        assertThat(customerDTO).isNotNull();
    }

    @Test
    void testListCustomers() {
        List<CustomerDTO> customerDTOS = customerController.listCustomers();

        assertThat(customerDTOS).hasSize(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> customerDTOS = customerController.listCustomers();

        assertThat(customerDTOS).isEmpty();
    }
}