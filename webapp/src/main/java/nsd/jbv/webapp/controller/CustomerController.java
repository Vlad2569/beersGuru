package nsd.jbv.webapp.controller;

import lombok.RequiredArgsConstructor;
import nsd.jbv.webapp.exception.NotFoundException;
import nsd.jbv.webapp.model.CustomerDTO;
import nsd.jbv.webapp.service.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_ID_PATH = CUSTOMER_PATH + "/{customerId}";

    private final CustomerService customerService;

    @DeleteMapping(CUSTOMER_ID_PATH)
    public ResponseEntity<String> handleDelete(@PathVariable("customerId") UUID customerId) {

        if (Boolean.FALSE.equals(customerService.deleteCustomerById(customerId))) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CUSTOMER_ID_PATH)
    public ResponseEntity<String> handlePut(@PathVariable("customerId") UUID customerId,
                                            @RequestBody CustomerDTO customerDTO) {

        if (customerService.updateCustomerById(customerId, customerDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<CustomerDTO> handlePost(@Validated @RequestBody CustomerDTO customerDTO) {

        CustomerDTO savedCustomerDTO = customerService.saveCustomer(customerDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomerDTO.getId().toString());

        return new ResponseEntity<>(savedCustomerDTO, headers, HttpStatus.CREATED);

    }

    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> listCustomers() {

        return customerService.listCustomers();
    }

    @GetMapping(CUSTOMER_ID_PATH)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID customerId) {

        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }
}
