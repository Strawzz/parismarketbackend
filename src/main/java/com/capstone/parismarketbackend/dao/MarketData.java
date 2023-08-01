
package com.capstone.parismarketbackend.dao;
//
import com.capstone.parismarketbackend.model.Market;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
////
@SpringBootApplication
public class MarketData {
//
    public static void main(String[] args) {
//        Map<String,String> dayMap = new HashMap<>();
//        dayMap.put("lundi","Monday");
//        dayMap.put("mardi", "Tuesday");
//        dayMap.put("mercredi", "Wedensday");
//        dayMap.put("jeudi", "Thursday");
//        dayMap.put("vendredi", "Friday");
//        dayMap.put("samedi", "Saturday");
//        dayMap.put("dimanche", "Sunday" );
//        dayMap.put("tous les jours", "Everyday");
////
        SpringApplication.run(MarketData.class, args);
//
//        String parisApiUrl = "https://opendata.paris.fr/api/explore/v2.0/catalog/datasets/marches-decouverts/records?limit=100";
//
//        WebClient.Builder parisApiBuilder = WebClient.builder()
//                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024));
//
//        Mono<Map<String, Object>> marketDataMono = parisApiBuilder.build()
//                .get()
//                .uri(parisApiUrl)
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<>() {});
//
//        marketDataMono.subscribe(marketData -> {
//            List<Map<String, Object>> records = (List<Map<String, Object>>) marketData.get("records");
//            if (records != null && !records.isEmpty()) {
//                System.out.print(records.size());
//                for (int i = 0; i < records.size(); i++) {
//                    Map<String, Object> record = (Map<String, Object>) records.get(i).get("record");
//                    if (record != null) {
//                        Map<String, Object> fields = (Map<String, Object>) record.get("fields");
//                        if (fields != null) {
//                            Market market = new Market();
//                            String nomLong = (String) fields.get("nom_long");
//                            String category = (String) fields.get("produit");
//                            Integer parisQuarter = (Integer) fields.get("ardt");
//                            String address = (String) fields.get("localisation");
//                            String days = (String)fields.get("jours_tenue");
//                            String[] daysList = days.split(",");
//                            String daysEnglish = "";
//                            for (String day: daysList){
//                                if(day != null){
//                                    day = day.trim();
//                                    String dayEnglish = dayMap.get(day);
//                                    if(dayEnglish != null){
//                                        daysEnglish += dayEnglish;
//                                    }
//                                    else
//                                        daysEnglish += day;
//                                    daysEnglish += " ";
//                                }
//
//                            }
//                            System.out.println(daysList);
////                          String hours = (String) (fields.get("jours_tenue") + ": " + fields.get("h_deb_sem_1") + " to " + fields.get("h_fin_sem_1"))
//                            String hours = (String) (daysEnglish + ": " + fields.get("h_deb_sem_1") + " to " + fields.get("h_fin_sem_1"));
//                            market.setName(nomLong);
//                            market.setCategory(category);
//                            market.setParisQuarter(parisQuarter);
//                            market.setAddress(address);
//                            market.setHours(hours);
//
//                            System.out.println("__________________________________");
//                            System.out.println("Market Id: " + (i+1));
//                            System.out.println("Market Name: " + market.getName());
//                            System.out.println("Category: " + market.getCategory());
//                            System.out.println("Paris City Quarter (Arrondissment): " + market.getParisQuarter());
//                            System.out.println("Address: " + market.getAddress());
//                            System.out.println("Hours: " + market.getHours());
//                            System.out.println("__________________________________");
//                        } else {
//                            System.out.println("Market name not found.");
//                        }
//                    } else {
//                        System.out.println("No market record.");
//                    }
//                }
//            } else {
//                System.out.println("No records found in the response.");
//            }
//        });
    }
}
//package com.capstone.parismarketbackend.dao;
//
//import com.capstone.parismarketbackend.model.Market;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.core.ParameterizedTypeReference;
//import reactor.core.publisher.Mono;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.HashMap;
//
//@RestController
//public class MarketData {
//    public static void main(String[] args) {
//
//        @GetMapping("/api/markets")
//        public Mono<List<Market>> getMarkets () {
//            // Map to convert French day names to English
//            Map<String, String> dayMap = new HashMap<>();
//            dayMap.put("lundi", "Monday");
//            dayMap.put("mardi", "Tuesday");
//            dayMap.put("mercredi", "Wednesday");
//            dayMap.put("jeudi", "Thursday");
//            dayMap.put("vendredi", "Friday");
//            dayMap.put("samedi", "Saturday");
//            dayMap.put("dimanche", "Sunday");
//            dayMap.put("tous les jours", "Everyday");
//
//            // Paris API URL to fetch market data
//            String parisApiUrl = "https://opendata.paris.fr/api/explore/v2.0/catalog/datasets/marches-decouverts/records?limit=100";
//
//            WebClient.Builder parisApiBuilder = WebClient.builder()
//                    .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024));
//
//            Mono<Map<String, Object>> marketDataMono = parisApiBuilder.build()
//                    .get()
//                    .uri(parisApiUrl)
//                    .accept(MediaType.APPLICATION_JSON)
//                    .retrieve()
//                    .bodyToMono(new ParameterizedTypeReference<>() {
//                    });
//
//            // Process the fetched market data and return as a Mono<List<Market>>
//            return marketDataMono.map(marketData -> {
//                List<Market> markets = new ArrayList<>();
//                List<Map<String, Object>> records = (List<Map<String, Object>>) marketData.get("records");
//                if (records != null && !records.isEmpty()) {
//                    for (int i = 0; i < records.size(); i++) {
//                        Map<String, Object> record = (Map<String, Object>) records.get(i).get("record");
//                        if (record != null) {
//                            Map<String, Object> fields = (Map<String, Object>) record.get("fields");
//                            if (fields != null) {
//                                Market market = new Market();
//                                String nomLong = (String) fields.get("nom_long");
//                                String category = (String) fields.get("produit");
//                                Integer parisQuarter = (Integer) fields.get("ardt");
//                                String address = (String) fields.get("localisation");
//                                String days = (String) fields.get("jours_tenue");
//                                String[] daysList = days.split(",");
//                                String daysEnglish = "";
//                                for (String day : daysList) {
//                                    if (day != null) {
//                                        day = day.trim();
//                                        String dayEnglish = dayMap.get(day);
//                                        if (dayEnglish != null) {
//                                            daysEnglish += dayEnglish;
//                                        } else {
//                                            daysEnglish += day;
//                                        }
//                                        daysEnglish += " ";
//                                    }
//                                }
//                                String hours = (String) (daysEnglish + ": " + fields.get("h_deb_sem_1") + " to " + fields.get("h_fin_sem_1"));
//                                market.setName(nomLong);
//                                market.setCategory(category);
//                                market.setParisQuarter(parisQuarter);
//                                market.setAddress(address);
//                                market.setHours(hours);
//
//                                markets.add(market);
//                            } else {
//                                System.out.println("Market name not found.");
//                            }
//                        } else {
//                            System.out.println("No market record.");
//                        }
//                    }
//                } else {
//                    System.out.println("No records found in the response.");
//                }
//                return markets;
//            });
//        }
//    }
//}
