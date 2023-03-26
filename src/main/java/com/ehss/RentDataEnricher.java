package com.ehss;


import com.ehss.model.*;
import com.ehss.repo.USCitiesRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.kstream.TransformerSupplier;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class RentDataEnricher implements TransformerSupplier<String, ZillowCityRents, KeyValue<String, CityRentsEnriched>> {

    @Autowired
    USCitiesRepository usCitiesRepository;

    //public String storeName;

    @Override
    public Transformer<String, ZillowCityRents, KeyValue<String, CityRentsEnriched>> get() {
        return new Transformer<String, ZillowCityRents, KeyValue<String, CityRentsEnriched>>() {

            //private KeyValueStore<String, ZillowCityRents> zillowCityRentsKeyValueStore;
            @Override
            public void init(ProcessorContext processorContext) {
                //zillowCityRentsKeyValueStore = (KeyValueStore<String, ZillowCityRents>) processorContext.getStateStore(storeName);
            }

            public String getDateStr(int idx){
                switch(idx){
                    case 8: return "2015-03-31";
                    case 9: return "2015-04-30";
                    case 10: return "2015-05-31";
                    case 11: return "2015-06-30";
                    case 12: return "2015-07-31";
                    case 13: return "2015-08-31";
                    case 14: return "2015-09-30";
                    case 15: return "2015-10-31";
                    case 16: return "2015-11-30";
                    case 17: return "2015-12-31";
                    case 18: return "2016-01-31";
                    case 19: return "2016-02-29";
                    case 20: return "2016-03-31";
                    case 21: return "2016-04-30";
                    case 22: return "2016-05-31";
                    case 23: return "2016-06-30";
                    case 24: return "2016-07-31";
                    case 25: return "2016-08-31";
                    case 26: return "2016-09-30";
                    case 27: return "2016-10-31";
                    case 28: return "2016-11-30";
                    case 29: return "2016-12-31";
                    case 30: return "2017-01-31";
                    case 31: return "2017-02-28";
                    case 32: return "2017-03-31";
                    case 33: return "2017-04-30";
                    case 34: return "2017-05-31";
                    case 35: return "2017-06-30";
                    case 36: return "2017-07-31";
                    case 37: return "2017-08-31";
                    case 38: return "2017-09-30";
                    case 39: return "2017-10-31";
                    case 40: return "2017-11-30";
                    case 41: return "2017-12-31";
                    case 42: return "2018-01-31";
                    case 43: return "2018-02-28";
                    case 44: return "2018-03-31";
                    case 45: return "2018-04-30";
                    case 46: return "2018-05-31";
                    case 47: return "2018-06-30";
                    case 48: return "2018-07-31";
                    case 49: return "2018-08-31";
                    case 50: return "2018-09-30";
                    case 51: return "2018-10-31";
                    case 52: return "2018-11-30";
                    case 53: return "2018-12-31";
                    case 54: return "2019-01-31";
                    case 55: return "2019-02-28";
                    case 56: return "2019-03-31";
                    case 57: return "2019-04-30";
                    case 58: return "2019-05-31";
                    case 59: return "2019-06-30";
                    case 60: return "2019-07-31";
                    case 61: return "2019-08-31";
                    case 62: return "2019-09-30";
                    case 63: return "2019-10-31";
                    case 64: return "2019-11-30";
                    case 65: return "2019-12-31";
                    case 78: return "2021-01-31";
                    case 79: return "2021-02-28";
                    case 80: return "2021-03-31";
                    case 81: return "2021-04-30";
                    case 82: return "2021-05-31";
                    case 83: return "2021-06-30";
                    case 84: return "2021-07-31";
                    case 85: return "2021-08-31";
                    case 86: return "2021-09-30";
                    case 87: return "2021-10-31";
                    case 88: return "2021-11-30";
                    case 89: return "2021-12-31";
                    case 90: return "2022-01-31";
                    case 91: return "2022-02-28";
                    case 92: return "2022-03-31";
                    case 93: return "2022-04-30";
                    case 94: return "2022-05-31";
                    case 95: return "2022-06-30";
                    case 96: return "2022-07-31";
                    case 97: return "2022-08-31";
                    case 98: return "2022-09-30";
                    case 99: return "2022-10-31";
                    case 100: return "2022-11-30";
                    case 101: return "2022-12-31";
                    case 102: return "2023-01-31";
                    case 103: return "2023-02-28";
                    default: return null;
                }
            }
            @Override
            public KeyValue<String, CityRentsEnriched> transform(String s, ZillowCityRents zillowCityRents) {
                String cityName = ""+zillowCityRents.getRegionName();
                log.info("Transforming "+cityName);

                USCities usCities = usCitiesRepository.findFirstByCity(cityName);
                if (usCities==null && cityName.startsWith("Saint ")){
                    cityName = cityName.replace("Saint","St.");
                    usCities = usCitiesRepository.findFirstByCity(cityName);
                }
                Location location = null;
                Double density = null;
                Long population = null;
                if (usCities!=null) {
                    location = Location.newBuilder().setLat(usCities.getLat()).setLon(usCities.getLng()).build();
                    density = usCities.getDensity();
                    population = usCities.getPopulation();
                }
                List<Rent> rentList = new ArrayList<>();
                for(int i=8;i<=65;i++){
                    String dateStr = getDateStr(i);
                    Double rentValue = (Double)zillowCityRents.get(i);
                    Long epochTimeInMillis = null;
                    try{
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = simpleDateFormat.parse(dateStr);
                        // get epoch time in milliseconds
                        epochTimeInMillis = Long.valueOf(date.getTime());
                        Rent rent = Rent.newBuilder().setMonthlyRent(rentValue).setTimestamp(epochTimeInMillis).build();
                        rentList.add(rent);

                    }catch (Exception ex){
                        log.warn("error parsing date "+ex.getMessage());
                    }
                }
                for(int j=78;j<=103;j++) {
                    String dateStr = getDateStr(j);
                    Double rentValue = (Double)zillowCityRents.get(j);
                    Long epochTimeInMillis = null;
                    try{
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = simpleDateFormat.parse(dateStr);
                        // get epoch time in milliseconds
                        epochTimeInMillis = Long.valueOf(date.getTime());
                        Rent rent = Rent.newBuilder().setMonthlyRent(rentValue).setTimestamp(epochTimeInMillis).build();
                        rentList.add(rent);

                    }catch (Exception ex){
                        log.warn("error parsing date "+ex.getMessage());
                    }
                }
                CityRentsEnriched cityRentsEnriched = CityRentsEnriched.newBuilder().setLocation(location)
                        .setRents(rentList)
                        .setCountyName(zillowCityRents.getCountyName())
                        .setDensity(density)
                        .setMetro(zillowCityRents.getMetro())
                        .setPopulation(population)
                        .setRegionID(zillowCityRents.getRegionID())
                        .setRegionName(zillowCityRents.getRegionName())
                        .setRegionType(zillowCityRents.getRegionType())
                        .setSizeRank(zillowCityRents.getSizeRank())
                        .setState(zillowCityRents.getState())
                        .setStateName(zillowCityRents.getStateName())
                        .build();

                return new KeyValue<>(String.valueOf(cityRentsEnriched.getRegionID()), cityRentsEnriched);
            }

            @Override
            public void close() {

            }
        };
    }
}
