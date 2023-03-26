package com.ehss.repo;

import com.ehss.model.USCities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface USCitiesRepository extends JpaRepository<USCities, String> {

    public USCities findFirstByCity(String city);
}
