package nsd.jbv.webapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import nsd.jbv.webapp.model.Customer;

@Service
public class CustomerServiceImpl implements CustomerService{

    private Map<UUID, Customer> customerMap;


    public CustomerServiceImpl() {
        
        this.customerMap = new HashMap<>();

        Customer customerOne = Customer.builder()
        .id(UUID.randomUUID())
        .version(1)
        .name("Samuel Smith")
        .createdDate(LocalDateTime.now())
        .updateDate(LocalDateTime.now())
        .build();

        Customer customerTwo = Customer.builder()
       .id(UUID.randomUUID())
       .version(1)
       .name("John Smith")
       .createdDate(LocalDateTime.now())
       .updateDate(LocalDateTime.now())
       .build();

       Customer customerThree = Customer.builder()
       .id(UUID.randomUUID())
       .version(1)
       .name("Dave Smith")
       .createdDate(LocalDateTime.now())
       .updateDate(LocalDateTime.now())
       .build();

       customerMap.put(customerOne.getId(), customerOne);
       customerMap.put(customerTwo.getId(), customerTwo);
       customerMap.put(customerThree.getId(), customerThree);
    }

    @Override
    public List<Customer> listCustomers() {
        
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        
        return customerMap.getOrDefault(id, null);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
    
        Customer savedCustomer = Customer.builder()
        .id(UUID.randomUUID())
        .version(1)
        .name(customer.getName())
        .createdDate(LocalDateTime.now())
        .updateDate(LocalDateTime.now())
        .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }

    @Override
    public Customer updateCustomerById(UUID customerId, Customer customer) {
        
        Customer existingCustomer = customerMap.getOrDefault(customerId, customer);

        existingCustomer.setName(customer.getName());

        return existingCustomer;
    }

    @Override
    public Customer deleteCustomerById(UUID customerId) {
        
        Customer existingCustomer = customerMap.get(customerId);
        customerMap.remove(customerId);
        return existingCustomer;
    }
    
}
