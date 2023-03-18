/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CustomerDTO {

    private UUID customerId;
    private Integer version;
    @NotNull
    @NotEmpty
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdate;

}