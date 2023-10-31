package nsd.jbv.webapp.service;

import lombok.RequiredArgsConstructor;
import nsd.jbv.webapp.entity.Customer;
import nsd.jbv.webapp.mappers.CustomerMapper;
import nsd.jbv.webapp.model.CustomerDTO;
import nsd.jbv.webapp.repository.CustomerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Primary
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        return customerMapper.customerToCustomerDto(customerRepository
                .save(customerMapper.customerDtoToCustomer(customerDTO)));
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customerDTO) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isPresent()) {
            Customer foundCustomer = customer.get();
            foundCustomer.setName(customerDTO.getName());
            foundCustomer.setUpdateDate(LocalDateTime.now());
            return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.save(foundCustomer)));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }
}
