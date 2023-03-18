/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.controllers;

import com.jbv.web.spring6restmvs.exceptions.NotFoundException;
import com.jbv.web.spring6restmvs.dtos.BeerDTO;
import com.jbv.web.spring6restmvs.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @GetMapping(BEER_PATH)
    public List<BeerDTO> listBeers() {

        log.debug("List Beers in controller was called.");

        return beerService.listBeers();
    }

    @GetMapping(BEER_PATH_ID)
    public BeerDTO getById(@PathVariable("beerId") UUID uuid) {

        log.debug("Get Beer by Id in controller was called.");

        return beerService.getById(uuid).orElseThrow(NotFoundException::new);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity createBeer(@Validated @RequestBody BeerDTO beerDTO) {

        log.debug("Create Beer in controller was called.");

        BeerDTO savedBeerDTO = beerService.saveNewBeer(beerDTO);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", BEER_PATH + "/" + savedBeerDTO.getBeerId().toString());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beerDTO) {

        log.debug("Update by id in controller was called.");

        if (beerService.updateById(beerId, beerDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId) {

        log.debug("Delete by id in controller was called.");

        if (! beerService.deleteById(beerId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity patchById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beerDTO) {

        log.debug("Patch by id in controller was called.");

        if (beerService.patchById(beerId, beerDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}