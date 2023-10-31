package nsd.jbv.webapp.mappers;

import nsd.jbv.webapp.entity.Beer;
import nsd.jbv.webapp.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO beerDTO);

    BeerDTO beerToBeerDto(Beer beer);
}
