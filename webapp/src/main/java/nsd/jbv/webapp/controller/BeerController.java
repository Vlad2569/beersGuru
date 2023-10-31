package nsd.jbv.webapp.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsd.jbv.webapp.exception.NotFoundException;
import nsd.jbv.webapp.model.BeerDTO;
import nsd.jbv.webapp.service.BeerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_ID_PATH = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @DeleteMapping(BEER_ID_PATH)
    public ResponseEntity<String> handleDelete(@PathVariable("beerId") UUID beerId) {

        if (Boolean.FALSE.equals(beerService.deleteBeerById(beerId))) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_ID_PATH)
    public ResponseEntity<String> handlePut(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beerDTO) {


        if (beerService.updateBeerById(beerId, beerDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity<BeerDTO> handlePost(@Validated @RequestBody BeerDTO beerDTO) {

        BeerDTO savedBeerDTO = beerService.saveBeer(beerDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeerDTO.getId().toString());

        return new ResponseEntity<>(savedBeerDTO, headers, HttpStatus.CREATED);
    }

    @GetMapping(BEER_PATH)
    public List<BeerDTO> listBeers() {

        return beerService.listBeers();
    }

    @GetMapping(BEER_ID_PATH)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {

        log.debug("Get Beer By Id in controller was called.");

        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }
}
