/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.mappers;

import com.jbv.web.spring6restmvs.dtos.BeerDTO;
import com.jbv.web.spring6restmvs.models.Beer;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDto(Beer beer);
}