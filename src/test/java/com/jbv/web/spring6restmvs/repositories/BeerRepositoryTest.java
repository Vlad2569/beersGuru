/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.repositories;

import com.jbv.web.spring6restmvs.enums.BeerStyle;
import com.jbv.web.spring6restmvs.models.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {

        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("New Beer")
                        .beerStyle(BeerStyle.PALE_LAGER)
                        .upc("626444783")
                        .price(new BigDecimal("11.21"))
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getBeerId()).isNotNull();
    }
}