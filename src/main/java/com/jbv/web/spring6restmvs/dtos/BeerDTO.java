/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.dtos;

import com.jbv.web.spring6restmvs.enums.BeerStyle;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @NotEmpty
    private String beerName;
    @NotNull
    private BeerStyle beerStyle;
    @NotNull
    @NotEmpty
    private String upc;
    private Integer quantityOnHand;
    @NotNull
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdate;
}