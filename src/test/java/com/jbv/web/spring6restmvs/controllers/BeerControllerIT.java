/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.controllers;

import com.jbv.web.spring6restmvs.dtos.BeerDTO;
import com.jbv.web.spring6restmvs.exceptions.NotFoundException;
import com.jbv.web.spring6restmvs.mappers.BeerMapper;
import com.jbv.web.spring6restmvs.models.Beer;
import com.jbv.web.spring6restmvs.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;
    
    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Test
    void testListBeers() {

        List<BeerDTO> beerDTOS = beerController.listBeers();

        assertThat(beerDTOS.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {

        beerRepository.deleteAll();

        List<BeerDTO> beerDTOS = beerController.listBeers();

        assertThat(beerDTOS.size()).isEqualTo(0);
    }

    @Test
    void testGetBuId() {

        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerDTO = beerController.getById(beer.getBeerId());

        assertThat(beerDTO).isNotNull();
    }

    @Test
    void testNotFound() {

        assertThrows(NotFoundException.class, () ->{
            beerController.getById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testCreateBeer() {

        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New Beer")
                .build();

        ResponseEntity responseEntity = beerController.createBeer(beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateBeer() {

        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        beerDTO.setBeerId(null);
        beerDTO.setVersion(null);
        final String beerName = "UPDATED";
        beerDTO.setBeerName(beerName);

        ResponseEntity responseEntity = beerController.updateById(beer.getBeerId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getBeerId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
    }

    @Test
    void testUpdateNotFound() {

        assertThrows(NotFoundException.class, () ->{
            beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteBeer() {

        Beer beer = beerRepository.findAll().get(0);

        ResponseEntity responseEntity = beerController.deleteById(beer.getBeerId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(beerRepository.findById(beer.getBeerId())).isEmpty();
    }

    @Test
    void testDeleteNotFound() {

        assertThrows(NotFoundException.class, () -> {
            beerController.deleteById(UUID.randomUUID());
        });
    }
}