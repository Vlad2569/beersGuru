/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.repositories;

import com.jbv.web.spring6restmvs.models.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
                .build());

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getBeerId()).isNotNull();
    }
}