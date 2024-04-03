package com.bs.service;

import com.bs.model.City;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CityService {
    public City addCity(City city);
    public City updateCity(City city);
    public City getCityById(Long id);
    public List<City> getCities();
    public void deleteCity(Long id);
}
