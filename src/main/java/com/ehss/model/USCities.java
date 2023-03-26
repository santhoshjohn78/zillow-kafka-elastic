package com.ehss.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "uscities")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class USCities {

    @Id
    private String id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "city_ascii", nullable = true)
    private String cityAscii;

    @Column(name = "state_id", nullable = true)
    private String stateId;

    @Column(name = "state_name", nullable = true)
    private String stateName;

    @Column(name = "county_fips", nullable = true)
    private String countyFips;

    @Column(name = "county_name", nullable = true)
    private String countyName;

    @Column(name = "lat", nullable = true)
    private Double lat;

    @Column(name = "lng", nullable = true)
    private Double lng;

    @Column(name = "population", nullable = true)
    private Long population;

    @Column(name = "density", nullable = true)
    private Double density;

    @Column(name = "source", nullable = true)
    private String source;

    @Column(name = "military", nullable = true)
    private Boolean military;

    @Column(name = "incorporated", nullable = true)
    private Boolean incorporated;

    @Column(name = "timezone", nullable = true)
    private String timezone;

    @Column(name = "ranking", nullable = true)
    private Integer ranking;

    @Column(name = "zips", nullable = true)
    private String zips;

}
