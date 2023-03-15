/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.services;

import com.jbv.web.spring6restmvs.enums.BeerStyle;
import com.jbv.web.spring6restmvs.models.Beer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    private final Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        Beer beer1 = Beer.builder()
                .beerId(UUID.randomUUID())
                .version(1)
                .beerName("Corona Extra")
                .beerStyle(BeerStyle.PALE_LAGER)
                .upc("9512468703")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .beerId(UUID.randomUUID())
                .version(1)
                .beerName("Heineken")
                .beerStyle(BeerStyle.PALE_LAGER)
                .upc("1596873240")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .beerId(UUID.randomUUID())
                .version(1)
                .beerName("Guinness Draught")
                .beerStyle(BeerStyle.IRISH_DRY_STOUT)
                .upc("8523690147")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getBeerId(), beer1);
        beerMap.put(beer2.getBeerId(), beer2);
        beerMap.put(beer3.getBeerId(), beer3);
    }

    @Override
    public List<Beer> listBeers(){
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer getById(UUID id) {

        log.debug("Get Beer by Id - in service. Id: " + id.toString());

        return beerMap.get(id);
    }

    @Override
    public Beer saveNewBeer(Beer beer) {

        Beer savedBeer = Beer.builder()
                .beerId(UUID.randomUUID())
                .version(beer.getVersion())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .build();

        beerMap.put(savedBeer.getBeerId(), savedBeer);

        return savedBeer;
    }

    @Override
    public void updateById(UUID beerId, Beer beer) {

        Beer beerToUpdate = beerMap.get(beerId);

        beerToUpdate.setBeerName(beer.getBeerName());
        beerToUpdate.setVersion(beer.getVersion());
        beerToUpdate.setBeerStyle(beer.getBeerStyle());
        beerToUpdate.setUpc(beer.getUpc());
        beerToUpdate.setPrice(beer.getPrice());
        beerToUpdate.setQuantityOnHand(beer.getQuantityOnHand());
        beerToUpdate.setLastUpdate(LocalDateTime.now());
    }

    @Override
    public void deleteById(UUID beerId) {
        beerMap.remove(beerId);
    }

    @Override
    public void patchById(UUID beerId, Beer beer) {

        Beer beerToPatch = beerMap.get(beerId);

        if (StringUtils.hasText(beer.getBeerName())) {
            beerToPatch.setBeerName(beer.getBeerName());
        }

        if (beer.getVersion() != null) {
            beerToPatch.setVersion(beer.getVersion());
        }

        if (beer.getBeerStyle() != null) {
            beerToPatch.setBeerStyle(beer.getBeerStyle());
        }

        if (StringUtils.hasText(beer.getUpc())) {
            beerToPatch.setUpc(beer.getUpc());
        }

        if (beer.getPrice() != null) {
            beerToPatch.setPrice(beer.getPrice());
        }

        if (beer.getQuantityOnHand() != null) {
            beerToPatch.setQuantityOnHand(beer.getQuantityOnHand());
        }

        beerToPatch.setLastUpdate(LocalDateTime.now());
    }


}