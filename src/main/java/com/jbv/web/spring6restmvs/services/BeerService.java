/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.services;

import com.jbv.web.spring6restmvs.models.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    Beer getById(UUID uuid);

    List<Beer> listBeers();

    Beer saveNewBeer(Beer beer);

    void updateById(UUID beerId, Beer beer);

    void deleteById(UUID beerId);

    void patchById(UUID beerId, Beer beer);
}