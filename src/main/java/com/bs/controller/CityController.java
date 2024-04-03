package com.bs.controller;

import com.bs.model.City;
import com.bs.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService){
        this.cityService = cityService;

    }
    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id){
        City city = cityService.getCityById(id);
        if (city != null)
            return ResponseEntity.ok(city);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<City>> getAllCities(){
        List<City> cities = cityService.getCities();
        return ResponseEntity.ok(cities);
    }

    @PostMapping
    public  ResponseEntity<City> addCity(@RequestBody City city){
        City createdCity = cityService.addCity(city);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City city){
        City existingCity = cityService.getCityById(id);
        if(existingCity !=null){
            city.setId(id);
            City updatedCity = cityService.updateCity(city);
            return ResponseEntity.ok(updatedCity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id){
        City existingCity = cityService.getCityById(id);
        if(existingCity != null){
            cityService.deleteCity(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
