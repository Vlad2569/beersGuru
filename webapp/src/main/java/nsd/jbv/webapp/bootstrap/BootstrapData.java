package nsd.jbv.webapp.bootstrap;

import lombok.RequiredArgsConstructor;
import nsd.jbv.webapp.entity.Beer;
import nsd.jbv.webapp.entity.Customer;
import nsd.jbv.webapp.model.BeerStyle;
import nsd.jbv.webapp.repository.BeerRepository;
import nsd.jbv.webapp.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {

        loadBeerData();
        loadCustomerData();
    }

    private void loadBeerData() {
        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .id(UUID.randomUUID())
                    .version(1)
                    .beerName("Corona Extra")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("9512468703")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .id(UUID.randomUUID())
                    .version(1)
                    .beerName("Heineken")
                    .beerStyle(BeerStyle.PALE_LAGER)
                    .upc("1596873240")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(392)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .id(UUID.randomUUID())
                    .version(1)
                    .beerName("Guinness Draught")
                    .beerStyle(BeerStyle.STOUT)
                    .upc("8523690147")
                    .price(new BigDecimal("13.99"))
                    .quantityOnHand(144)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .id(UUID.randomUUID())
                    .version(1)
                    .name("Vasko Milev")
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .id(UUID.randomUUID())
                    .version(1)
                    .name("Slavka Dineva")
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .id(UUID.randomUUID())
                    .version(1)
                    .name("Rumen Genev")
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }
    }
}
