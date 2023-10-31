package nsd.jbv.webapp.service;

import nsd.jbv.webapp.exception.NotFoundException;
import nsd.jbv.webapp.model.CustomerDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, CustomerDTO> customerMap;


    public CustomerServiceImpl() {

        this.customerMap = new HashMap<>();

        CustomerDTO customerDTOOne = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Samuel Smith")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customerDTOTwo = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("John Smith")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customerDTOThree = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Dave Smith")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerMap.put(customerDTOOne.getId(), customerDTOOne);
        customerMap.put(customerDTOTwo.getId(), customerDTOTwo);
        customerMap.put(customerDTOThree.getId(), customerDTOThree);
    }

    @Override
    public List<CustomerDTO> listCustomers() {

        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {

        return Optional.of(customerMap.get(id));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {

        CustomerDTO savedCustomerDTO = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name(customerDTO.getName())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerMap.put(savedCustomerDTO.getId(), savedCustomerDTO);

        return savedCustomerDTO;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customerDTO) {

        CustomerDTO existingCustomerDTO = customerMap.getOrDefault(customerId, null);

        if (existingCustomerDTO == null) {
            throw new NotFoundException("Customer does not exist.");
        }

        existingCustomerDTO.setName(customerDTO.getName());

        return Optional.of(existingCustomerDTO);
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {

        CustomerDTO existingCustomerDTO = customerMap.getOrDefault(customerId, null);

        if (existingCustomerDTO == null) {
            throw new NotFoundException("Customer does not exist.");
        }

        customerMap.remove(customerId);
        return true;
    }
}
