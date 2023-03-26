package com.ehss;

import com.ehss.model.CityRentsEnriched;
import com.ehss.model.ZillowCityRents;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

import java.util.HashMap;


@SpringBootApplication
public class ZillowRentProcessingApp {


    @Autowired
    RentDataEnricher rentDataEnricher;


    @Value("${app.input.topic}")
    private String topicName;


    @Value("${app.out.topic}")
    private String outTopicName;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.application-id}")
    private String applicationId;

    @Value("${schema.registry.url}")
    private String schemaRegistryUrl;

    @Bean
    public StreamsBuilderFactoryBean streamsBuilderFactoryBean() {
        HashMap props = new HashMap();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde.class);

        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

        KafkaStreamsConfiguration config = new KafkaStreamsConfiguration(props);

        return new StreamsBuilderFactoryBean(config);
    }




    @Bean
    public KStream<String, CityRentsEnriched> kStream(StreamsBuilder builder) {



        KStream<String, ZillowCityRents> stream = builder.stream(topicName);


        final KStream<String, CityRentsEnriched> enrichedStream =
                stream.transform(rentDataEnricher);
        enrichedStream.to(outTopicName);

//        enrichedStream.foreach((key, value) -> {
//            System.out.println("Key: " + key);
//            String cityName = value.getRegionName()+"";
//
//
//        });
        return enrichedStream;
    }

    public static void main(String[] args) {
        SpringApplication.run(ZillowRentProcessingApp.class, args);
    }
}
