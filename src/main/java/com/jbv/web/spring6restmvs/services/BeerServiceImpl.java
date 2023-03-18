/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.services;

import com.jbv.web.spring6restmvs.enums.BeerStyle;
import com.jbv.web.spring6restmvs.dtos.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private final Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        BeerDTO beerDTO1 = BeerDTO.builder()
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

        BeerDTO beerDTO2 = BeerDTO.builder()
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

        BeerDTO beerDTO3 = BeerDTO.builder()
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

        beerMap.put(beerDTO1.getBeerId(), beerDTO1);
        beerMap.put(beerDTO2.getBeerId(), beerDTO2);
        beerMap.put(beerDTO3.getBeerId(), beerDTO3);
    }

    @Override
    public List<BeerDTO> listBeers(){

        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<BeerDTO> getById(UUID id) {

        log.debug("Get Beer by Id - in service. Id: " + id.toString());

        return Optional.ofNullable(beerMap.get(id));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {

        BeerDTO savedBeerDTO = BeerDTO.builder()
                .beerId(UUID.randomUUID())
                .version(beerDTO.getVersion())
                .beerName(beerDTO.getBeerName())
                .beerStyle(beerDTO.getBeerStyle())
                .upc(beerDTO.getUpc())
                .price(beerDTO.getPrice())
                .quantityOnHand(beerDTO.getQuantityOnHand())
                .createdDate(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .build();

        beerMap.put(savedBeerDTO.getBeerId(), savedBeerDTO);

        return savedBeerDTO;
    }

    @Override
    public Optional<BeerDTO> updateById(UUID beerId, BeerDTO beerDTO) {

        BeerDTO beerDTOToUpdate = beerMap.get(beerId);

        beerDTOToUpdate.setBeerName(beerDTO.getBeerName());
        beerDTOToUpdate.setVersion(beerDTO.getVersion());
        beerDTOToUpdate.setBeerStyle(beerDTO.getBeerStyle());
        beerDTOToUpdate.setUpc(beerDTO.getUpc());
        beerDTOToUpdate.setPrice(beerDTO.getPrice());
        beerDTOToUpdate.setQuantityOnHand(beerDTO.getQuantityOnHand());
        beerDTOToUpdate.setLastUpdate(LocalDateTime.now());

        return Optional.of(beerDTOToUpdate);
    }

    @Override
    public Boolean deleteById(UUID beerId) {
        beerMap.remove(beerId);
        return true;
    }

    @Override
    public Optional<BeerDTO> patchById(UUID beerId, BeerDTO beerDTO) {

        BeerDTO beerDTOToPatch = beerMap.get(beerId);

        if (StringUtils.hasText(beerDTO.getBeerName())) {
            beerDTOToPatch.setBeerName(beerDTO.getBeerName());
        }

        if (beerDTO.getVersion() != null) {
            beerDTOToPatch.setVersion(beerDTO.getVersion());
        }

        if (beerDTO.getBeerStyle() != null) {
            beerDTOToPatch.setBeerStyle(beerDTO.getBeerStyle());
        }

        if (StringUtils.hasText(beerDTO.getUpc())) {
            beerDTOToPatch.setUpc(beerDTO.getUpc());
        }

        if (beerDTO.getPrice() != null) {
            beerDTOToPatch.setPrice(beerDTO.getPrice());
        }

        if (beerDTO.getQuantityOnHand() != null) {
            beerDTOToPatch.setQuantityOnHand(beerDTO.getQuantityOnHand());
        }

        beerDTOToPatch.setLastUpdate(LocalDateTime.now());
        return Optional.of(beerDTOToPatch);
    }


}