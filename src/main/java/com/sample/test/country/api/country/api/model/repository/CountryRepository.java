package com.sample.test.country.api.country.api.model.repository;

import com.sample.test.country.api.country.api.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
