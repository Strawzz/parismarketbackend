package com.capstone.parismarketbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.ParameterizedTypeReference; // Add this import statement
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ParismarketbackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(ParismarketbackendApplication.class, args);

        String parisApiUrl = "https://opendata.paris.fr/api/explore/v2.0/catalog/datasets/marches-decouverts/records";

        WebClient.Builder parisApiBuilder = WebClient.builder();

//        String marketData = parisApiBuilder.build()
//                .get()
//                .uri(parisApiUrl)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
////        System.out.println("----------------------------");
////        System.out.println(marketData);
////        System.out.println("----------------------------");
//
////        String name = marketData.get("records")[0].get("records").get("fields").get("nom_long");
////        System.out.println(name);
//        List<Map<String, Object>> records = (List<Map<String, Object>>) marketData.get("records");
//        String name = (String) records.get(0).get("fields").get("nom_long");
//        System.out.println(name);

        Mono<Map<String, Object>> marketDataMono = parisApiBuilder.build()
                .get()
                .uri(parisApiUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});

        marketDataMono.subscribe(marketData -> {
            List<Map<String, Object>> records = (List<Map<String, Object>>) marketData.get("records");
            if (records != null && !records.isEmpty()) {
                Map<String, Object> record = (Map<String, Object>) records.get(0).get("record");
                if (record != null) {
                    Map<String, Object> fields = (Map<String, Object>) record.get("fields");
                    if (fields != null) {
                        String nomLong = (String) fields.get("nom_long");
                        String category = (String) fields.get("produit");
                        Integer parisQuarter = (Integer) fields.get("ardt");
                        String address = (String) fields.get("localisation");
                        String hours = (String) (fields.get("jours_tenue") + ": " + fields.get("h_deb_sem_1") + " to " + fields.get("h_fin_sem_1"));

                        System.out.println("Market Name: " + nomLong);
                        System.out.println("Category: " + category);
                        System.out.println("Paris City Quarter (Arrondissment): " + parisQuarter);
                        System.out.println("Address: " + address);
                        System.out.println("Hours: " + hours);
                    } else {
                        System.out.println("Market name not found.");
                    }
                } else {
                    System.out.println("No market record.");
                }
            } else {
                System.out.println("No records found in the response.");
            }
        });


    }

}
