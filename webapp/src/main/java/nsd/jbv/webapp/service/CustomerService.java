package nsd.jbv.webapp.service;

import java.util.List;
import java.util.UUID;

import nsd.jbv.webapp.model.Customer;

public interface CustomerService {
    
    public List<Customer> listCustomers();

    public Customer getCustomerById(UUID id);

    public Customer saveCustomer(Customer customer);
}
