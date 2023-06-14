package com.epam.summer.lab.providers;

import com.epam.summer.lab.cities.Paris;
import com.epam.summer.lab.cities.Prague;
import com.epam.summer.lab.cities.Rome;
import com.epam.summer.lab.cities.Spain;
import com.epam.summer.lab.cities.interfaces.City;

import java.util.ArrayList;
import java.util.List;

public class CityProvider {
    private final List<City> cities = new ArrayList<>();

    public CityProvider() {
        init();
    }

    public void visitCity(final int destinationCity, final int timeMeal) {
        City city = cities.get(destinationCity);
        city.startExcursion(timeMeal);
    }

    public List<City> getCities() {
        return cities;
    }

    private void init() {
        cities.add(new Prague());
        cities.add(new Paris());
        cities.add(new Rome());
        cities.add(new Spain());
    }
}