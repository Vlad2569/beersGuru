package com.jbv.web.spring6restmvs.services;

import com.jbv.web.spring6restmvs.models.Beer;

import java.util.UUID;

public interface BeerService {

    Beer getById(UUID uuid);
}
