/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.dtos;

import com.jbv.web.spring6restmvs.enums.BeerStyle;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class BeerDTO {
    private UUID beerId;
    private Integer version;
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdate;
}