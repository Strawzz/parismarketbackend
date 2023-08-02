package com.capstone.parismarketbackend.service;

import com.capstone.parismarketbackend.model.Market;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MarketService {
    public String translateData(String dayFrench){
        Map<String, String> dayMap = new HashMap<>();
        dayMap.put("lundi", "Monday");
        dayMap.put("mardi", "Tuesday");
        dayMap.put("mercredi", "Wednesday");
        dayMap.put("jeudi", "Thursday");
        dayMap.put("vendredi", "Friday");
        dayMap.put("samedi", "Saturday");
        dayMap.put("dimanche", "Sunday");
        dayMap.put("tous les jours", "Everyday");

        String dayEnglish = dayMap.get(dayFrench);
        return dayEnglish;
    }

    public Market formatData(Map<String, Object> fields) {
        Market market = new Market();
        String nomLong = (String) fields.get("nom_long");
        String category = (String) fields.get("produit");
        Integer parisQuarter = (Integer) fields.get("ardt");
        String address = (String) fields.get("localisation");
        String days = (String) fields.get("jours_tenue");
        String[] daysList = days.split(",");
        String daysEnglish = "";
        for (String day : daysList) {
            if (day != null) {
                day = day.trim();
                String dayEnglish = translateData(day);
                if (dayEnglish != null) {
                    daysEnglish += dayEnglish;
                } else {
                    daysEnglish += day;
                }
                daysEnglish += " ";
            }
        }
        String hours = (String) (daysEnglish + ": " + fields.get("h_deb_sem_1") + " to " + fields.get("h_fin_sem_1"));
        market.setName(nomLong);
        market.setCategory(category);
        market.setParisQuarter(parisQuarter);
        market.setAddress(address);
        market.setHours(hours);
        return market;

    }
}
