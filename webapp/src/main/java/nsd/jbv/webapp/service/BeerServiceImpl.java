package nsd.jbv.webapp.service;

import lombok.extern.slf4j.Slf4j;
import nsd.jbv.webapp.exception.NotFoundException;
import nsd.jbv.webapp.model.BeerDTO;
import nsd.jbv.webapp.model.BeerStyle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private final Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {

        this.beerMap = new HashMap<>();

        BeerDTO beerDTOOne = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Guinness")
                .beerStyle(BeerStyle.STOUT)
                .upc("57913")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(123)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beerDTOTwo = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Heineken")
                .beerStyle(BeerStyle.PALE_LAGER)
                .upc("48620")
                .price(new BigDecimal("10.99"))
                .quantityOnHand(77)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beerDTOThree = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Budweiser")
                .beerStyle(BeerStyle.LAGER)
                .upc("38592")
                .price(new BigDecimal("8.99"))
                .quantityOnHand(107)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beerDTOOne.getId(), beerDTOOne);
        beerMap.put(beerDTOTwo.getId(), beerDTOTwo);
        beerMap.put(beerDTOThree.getId(), beerDTOThree);
    }

    @Override
    public List<BeerDTO> listBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {

        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {

        BeerDTO savedBeerDTO = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName(beerDTO.getBeerName())
                .beerStyle(beerDTO.getBeerStyle())
                .price(beerDTO.getPrice())
                .upc(beerDTO.getUpc())
                .quantityOnHand(beerDTO.getQuantityOnHand())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(savedBeerDTO.getId(), savedBeerDTO);

        return savedBeerDTO;

    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beerDTO) {
        BeerDTO existingBeerDTO = beerMap.get(beerId);

        if (existingBeerDTO == null) {
            return Optional.empty(); // Beer does not exist, return an empty Optional.
        }

        existingBeerDTO.setBeerName(beerDTO.getBeerName());
        existingBeerDTO.setBeerStyle(beerDTO.getBeerStyle());
        existingBeerDTO.setPrice(beerDTO.getPrice());
        existingBeerDTO.setUpc(beerDTO.getUpc());
        existingBeerDTO.setQuantityOnHand(beerDTO.getQuantityOnHand());
        existingBeerDTO.setUpdateDate(LocalDateTime.now());

        return Optional.of(existingBeerDTO); // Return the updated BeerDTO wrapped in an Optional.
    }


    @Override
    public Boolean deleteBeerById(UUID beerId) {

        BeerDTO deletedBeerDTO = beerMap.getOrDefault(beerId, null);
        if (deletedBeerDTO == null) {
            throw new NotFoundException("Beer does not exist.");
        }
        beerMap.remove(beerId);
        return true;
    }
}
