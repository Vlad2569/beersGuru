/*
 * Copyright (c) 2023. jbv
 */

package com.jbv.web.spring6restmvs.repositories;

import com.jbv.web.spring6restmvs.models.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

}