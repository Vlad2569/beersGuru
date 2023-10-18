package nsd.jbv.webapp.service;

import java.util.List;
import java.util.UUID;

import nsd.jbv.webapp.model.Beer;

public interface BeerService {

    public List<Beer> listBeers();
    
    public Beer getBeerById(UUID id);

    public Beer saveBeer(Beer beer);

    public Beer updateBeerById(UUID beerId, Beer beer);

    public Beer deleteBeerById(UUID beerId);
}
