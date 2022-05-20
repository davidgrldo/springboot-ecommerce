package com.sandbox.ecommerce.dao;

import com.sandbox.ecommerce.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@CrossOrigin("http://localhost:4200")
@RepositoryRestResource(collectionResourceRel="countries", path="countries")
public interface CountryRepository extends JpaRepository<Country, Integer> {
    //
}