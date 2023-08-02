

package com.capstone.parismarketbackend.externalApi;

import com.capstone.parismarketbackend.model.Market;
import com.capstone.parismarketbackend.service.MarketService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MarketExternalApi {

    private final MarketService marketService;

    public MarketExternalApi(MarketService marketService){
        this.marketService = marketService;
    }




    public Mono<List<Market>> getExternalAllMarkets() {

        // Paris API URL to fetch market data
        String parisApiUrl = "https://opendata.paris.fr/api/explore/v2.0/catalog/datasets/marches-decouverts/records?limit=100";

        WebClient.Builder parisApiBuilder = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024));

        Mono<Map<String, Object>> marketDataMono = parisApiBuilder.build()
                .get()
                .uri(parisApiUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });

        // Process the fetched market data and return as a Mono<List<Market>>
        return marketDataMono.map(marketData -> {
            List<Market> markets = new ArrayList<>();
            List<Map<String, Object>> records = (List<Map<String, Object>>) marketData.get("records");
            if (records != null && !records.isEmpty()) {
                for (int i = 0; i < records.size(); i++) {
                    Map<String, Object> record = (Map<String, Object>) records.get(i).get("record");
                    if (record != null) {
                        Map<String, Object> fields = (Map<String, Object>) record.get("fields");
                        if (fields != null) {
                            Market curMarket = marketService.formatData(fields);
                            if (curMarket != null) {
                                markets.add(curMarket);
                            } else {
                                System.out.println("Failed to process market data for record: " + record);
                            }
                        } else {
                            System.out.println("Market fields not found.");
                        }
                    } else {
                        System.out.println("No market record.");
                    }
                }
            } else {
                System.out.println("No records found in the response.");
            }
            return markets;
        });
    }
}
