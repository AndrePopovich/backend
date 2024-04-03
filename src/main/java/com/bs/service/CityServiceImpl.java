package com.bs.service;

import com.bs.model.City;
import com.bs.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService{

    private final CityRepository cityRepository;
    @Autowired
    public CityServiceImpl(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }
    @Override
    public City addCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public City updateCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public City getCityById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }
}
