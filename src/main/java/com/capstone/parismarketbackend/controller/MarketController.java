package com.capstone.parismarketbackend.controller;

import com.capstone.parismarketbackend.externalApi.MarketExternalApi;
import com.capstone.parismarketbackend.model.Market;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MarketController {

    private final MarketExternalApi marketExternalApi;

    public MarketController(MarketExternalApi marketExternalApi){
        this.marketExternalApi = marketExternalApi;
    }

//    @CrossOrigin
//    @GetMapping("/api/markets/all")
//    public Mono<List<Market>> getMarkets(){
//        Mono<List<Market>> allMarkets = marketExternalApi.getExternalAllMarkets();
//        return allMarkets;
//
//    }
@CrossOrigin
@GetMapping("/api/markets/all")
public Mono<List<Market>> getMarkets(
        @RequestParam(required = false) Integer quarterId,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String hours
) {
    Mono<List<Market>> allMarkets = marketExternalApi.getExternalAllMarkets();
    return allMarkets.map(marketList -> {
        if (quarterId != null) {
            // Filter the markets based on the quarter ID
            List<Market> marketsWithQuarterId = marketList.stream()
                    .filter(market -> market.getParisQuarter() == quarterId)
                    .collect(Collectors.toList());

            if (!marketsWithQuarterId.isEmpty()) {
                return marketsWithQuarterId;
            } else {
                throw new RuntimeException("No markets found with the quarter ID: " + quarterId);
            }
        } else if (category != null) {
            // Filter the markets based on the category
            List<Market> marketsWithCategory = marketList.stream()
                    .filter(market -> market.getCategory().equals(category))
                    .collect(Collectors.toList());

            if (!marketsWithCategory.isEmpty()) {
                return marketsWithCategory;
            } else {
                throw new RuntimeException("No markets found with the category: " + category);
            }
        } else if(hours != null){
            List<Market> marketsWithHours = marketList.stream()
                    .filter(market -> market.getHours().contains(hours))
                    .collect(Collectors.toList());
            if(!marketsWithHours.isEmpty()){
                return marketsWithHours;
            }else{
                throw new RuntimeException("No markets found with the hours: " + hours);
            }

        } else {
            // If no quarter ID or category is provided, return all markets
            return marketList;
        }
    });
}


    @CrossOrigin
    @GetMapping("/api/markets/marketByName")
    public Mono<Market> getMarketByName(@RequestParam String marketName) {
        String decodedMarketName = URLDecoder.decode(marketName, StandardCharsets.UTF_8);

        Mono<List<Market>> allMarkets = marketExternalApi.getExternalAllMarkets();
        return allMarkets.flatMap(marketList -> {
            for (Market market : marketList) {
                if (market.getName().equals(decodedMarketName)) {
                    return Mono.just(market); // Found the desired market, return it as a Mono
                }
            }

            // Market with the given name not found, throw an exception
            return Mono.error(new RuntimeException("Market not found with name: " + decodedMarketName));
        });
    }
//
//    @CrossOrigin
//    @GetMapping("/api/markets/marketByQuarter/quarterId")
//    public Mono<List<Market>> getMarketByName(@RequestParam Integer quarterId) {
//
//        Mono<List<Market>> allMarkets = marketExternalApi.getExternalAllMarkets();
//        List<Market> marketQuarterList = new ArrayList<>();
//        return allMarkets.flatMap(marketList -> {
//            for (Market market : marketList) {
//                if (market.getParisQuarter() == quarterId) {
//                    marketQuarterList.add(market);
//                    // Found the desired market, return it as a Mono
//                }
//            }
//            if(!marketQuarterList.isEmpty()){
//                return Mono.just(marketQuarterList);
//            }
//            // Market with the given name not found, throw an exception
//            return Mono.error(new RuntimeException("Market not found with the quarter id: " + quarterId));
//        });
//    }
//@CrossOrigin
//@GetMapping("/api/markets/marketByQuarter")
//public Mono<List<Market>> getMarketByQuarter(@RequestParam Integer quarterId) {
//
//    Mono<List<Market>> allMarkets = marketExternalApi.getExternalAllMarkets();
//    List<Market> marketQuarterList = new ArrayList<>();
//    return allMarkets.flatMap(marketList -> {
//        for (Market market : marketList) {
//            if (market.getParisQuarter() == quarterId) {
//                marketQuarterList.add(market);
//                // Found the desired market, return it as a Mono
//            }
//        }
//        if (!marketQuarterList.isEmpty()) {
//            return Mono.just(marketQuarterList);
//        }
//        // Market with the given quarterId not found, throw an exception
//        return Mono.error(new RuntimeException("Market not found with the quarter id: " + quarterId));
//    });
//}




}
