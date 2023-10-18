package nsd.jbv.webapp.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsd.jbv.webapp.model.Beer;
import nsd.jbv.webapp.service.BeerService;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
    
    private final BeerService beerService;

    @DeleteMapping("/{beerId}")
    public ResponseEntity<Beer> handleDelete(@PathVariable("beerId") UUID beerId) {

        Beer savedBeer = beerService.deleteBeerById(beerId);

        return new ResponseEntity<Beer>(savedBeer, HttpStatus.OK);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<Beer> handlePut(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {

        Beer savedBeer = beerService.updateBeerById(beerId, beer);

        return new ResponseEntity<Beer>(savedBeer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Beer> handlePost(@RequestBody Beer beer) {

        Beer savedBeer = beerService.saveBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        return new ResponseEntity<Beer>(savedBeer, headers, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Beer> listBeers() {
        
        return beerService.listBeers();
    }

    @GetMapping("/{beerId}")
    public Beer getBeerById(@PathVariable("beerId") UUID beerId) {

        log.debug("Get Beer By Id in controller was called.");

        return beerService.getBeerById(beerId);
    }
}
