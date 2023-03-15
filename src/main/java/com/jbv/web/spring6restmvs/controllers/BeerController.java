package com.jbv.web.spring6restmvs.controllers;

import com.jbv.web.spring6restmvs.models.Beer;
import com.jbv.web.spring6restmvs.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;

    public Beer getById(UUID uuid) {

        log.debug("Get Beer by Id in controller was called.");

        return beerService.getById(uuid);
    }
}
