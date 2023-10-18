package nsd.jbv.webapp.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import nsd.jbv.webapp.model.Customer;
import nsd.jbv.webapp.service.CustomerService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    
    private final CustomerService customerService;

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Customer> handleDelete(@PathVariable("customerId") UUID customerId) {

        Customer savedCustomer = customerService.deleteCustomerById(customerId);

        return new ResponseEntity<Customer>(savedCustomer, HttpStatus.OK);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> handlePut(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer) {

        Customer savedCustomer = customerService.updateCustomerById(customerId, customer);

        return new ResponseEntity<Customer>(savedCustomer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> handlePost(@RequestBody Customer customer) {

        Customer savedCustomer = customerService.saveCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());

        return new ResponseEntity<Customer>(savedCustomer, headers, HttpStatus.CREATED);

    }

    @GetMapping
    public List<Customer> listCustomers() {
        
        return customerService.listCustomers();
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {

        return customerService.getCustomerById(customerId);
    }
}
