package nsd.jbv.webapp.controller;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import nsd.jbv.webapp.model.Beer;
import nsd.jbv.webapp.service.BeerService;
import nsd.jbv.webapp.service.BeerServiceImpl;

@WebMvcTest(BeerController.class)
public class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testListBeers() throws Exception {

        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void testGetBeerById() throws Exception {

        Beer testBeer = beerServiceImpl.listBeers().get(0);

        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

        mockMvc.perform(get(BeerController.BEER_ID_PATH, testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }

    @Test
    void testHandlePost() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);
        beer.setId(null);
        beer.setVersion(null);

        given(beerService.saveBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1));

        mockMvc.perform(post(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testHandlePut() throws Exception {

        Beer beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(put(BeerController.BEER_ID_PATH, beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isOk());

        verify(beerService).updateBeerById(any(UUID.class), any(Beer.class));
    }

    @Test
    void testHandleDelete() throws Exception {

        Beer beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(delete(BeerController.BEER_ID_PATH, beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<UUID> uuid = ArgumentCaptor.forClass(UUID.class);
        verify(beerService).deleteBeerById(uuid.capture());

        assertThat(beer.getId()).isEqualTo(uuid.getValue());
    }
}
