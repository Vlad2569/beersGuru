package com.jbv.web.spring6restmvs.services;

import com.jbv.web.spring6restmvs.enums.BeerStyle;
import com.jbv.web.spring6restmvs.models.Beer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public Beer getById(UUID uuid) {

        log.debug("Get Beer by Id in service was called.");

        return Beer.builder()
                .id(uuid)
                .version(1)
                .beerName("Corona Extra")
                .beerStyle(BeerStyle.PALE_LAGER)
                .upc("951428637")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(123)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
