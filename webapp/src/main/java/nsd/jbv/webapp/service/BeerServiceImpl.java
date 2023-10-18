package nsd.jbv.webapp.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import nsd.jbv.webapp.model.Beer;
import nsd.jbv.webapp.model.BeerStyle;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {

        this.beerMap = new HashMap<>();

        Beer beerOne = Beer.builder()
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

        Beer beerTwo = Beer.builder()
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

        Beer beerThree = Beer.builder()
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

        beerMap.put(beerOne.getId(), beerOne);
        beerMap.put(beerTwo.getId(), beerTwo);
        beerMap.put(beerThree.getId(), beerThree);
    }

    @Override
    public List<Beer> listBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer getBeerById(UUID id) {

        log.debug("Get Beer By Id in service was called");

        return beerMap.getOrDefault(id, null);
    }

    @Override
    public Beer saveBeer(Beer beer) {
        
        Beer savedBeer = Beer.builder()
        .id(UUID.randomUUID())
        .version(1)
        .beerName(beer.getBeerName())
        .beerStyle(beer.getBeerStyle())
        .price(beer.getPrice())
        .upc(beer.getUpc())
        .quantityOnHand(beer.getQuantityOnHand())
        .createdDate(LocalDateTime.now())
        .updateDate(LocalDateTime.now())
        .build();

        beerMap.put(savedBeer.getId(), savedBeer);

        return savedBeer;

    }

}
