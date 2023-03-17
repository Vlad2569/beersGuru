/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CustomerDTO {

    private UUID customerId;
    private Integer version;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdate;

}