/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class Customer {

    private UUID customerId;
    private String name;
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdate;

}