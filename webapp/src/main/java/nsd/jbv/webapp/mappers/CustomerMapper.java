package nsd.jbv.webapp.mappers;

import nsd.jbv.webapp.entity.Customer;
import nsd.jbv.webapp.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDto(Customer customer);
}
