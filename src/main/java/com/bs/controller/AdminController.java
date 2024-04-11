package com.bs.controller;

import com.bs.model.Category;
import com.bs.model.City;
import com.bs.service.CategoryService;
import com.bs.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final CategoryService categoryService;
    private final CityService cityService;

    public AdminController(CategoryService categoryService, CityService cityService){
        this.categoryService = categoryService;
        this.cityService = cityService;
    }
    @GetMapping("/cities/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id){
        City city = cityService.getCityById(id);
        if (city != null)
            return ResponseEntity.ok(city);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getAllCities(){
        List<City> cities = cityService.getCities();
        return ResponseEntity.ok(cities);
    }

    @PostMapping("/cities")
    public  ResponseEntity<City> addCity(@RequestBody City city){
        City createdCity = cityService.addCity(city);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    }

    @PutMapping("/cities/{id}")
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

    @DeleteMapping("/cities/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id){
        City existingCity = cityService.getCityById(id);
        if(existingCity != null){
            cityService.deleteCity(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*********************************************************/
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
        Category category = categoryService.getCategoryById(id);
        if(category != null)
            return ResponseEntity.ok(category);
        else
            return ResponseEntity.notFound().build();
    }



    @PostMapping("/categories")
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        Category createdCategory = categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category){
        Category existingCategory = categoryService.getCategoryById(id);
        if(existingCategory != null){
            category.setId(id);
            Category updatedCategory = categoryService.updateCategory(category);
            return ResponseEntity.ok(updatedCategory);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        Category existingCategory = categoryService.getCategoryById(id);
        if(existingCategory != null) {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
