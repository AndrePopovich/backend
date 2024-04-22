package com.bs.controller;

import com.bs.model.Ad;
import com.bs.model.Category;
import com.bs.model.City;
import com.bs.model.Image;
import com.bs.service.AdService;
import com.bs.service.CategoryService;
import com.bs.service.CityService;
import com.bs.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final AdService adService;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final ImageService imageService;

    public SearchController (AdService adService, CategoryService categoryService, CityService cityService, ImageService imageService){
        this.adService = adService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.imageService = imageService;
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id){
        Image image = imageService.getImageById(id);
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }
    @GetMapping("/ads-images/{idAd}")
    public ResponseEntity<List<Image>> getImagesByAdId(@PathVariable Long idAd){
        List<Image> images = adService.getImagesForAd(idAd);
        if(!images.isEmpty()){
            System.out.println("Кількість фото вже прямує по контролеру: " + images.size());
            return ResponseEntity.ok(images);}
        else{
            return ResponseEntity.notFound().build();}
    }
    @GetMapping()
    public ResponseEntity<List<Ad>> searchAds(
            @RequestParam(name = "adName", required = false) String adName,
            @RequestParam(name = "categoryName", required = false) String categoryName,
            @RequestParam(name = "cityName", required = false) String cityName,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "sortBy", required = false, defaultValue = "default") String sortBy
    ) {
        List<Ad> ads = adService.searchAds(adName, categoryName, cityName, minPrice, maxPrice, sortBy);
        for(Ad ad : ads){
            System.out.println(ad.toString());
        }
        return ResponseEntity.ok(ads);
    }
    @GetMapping("/preview/{id}")
    public ResponseEntity<?> getPreviewImageByAdId(@PathVariable Long id){
        Image image = adService.getPreviewImageForAd(id);
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }
    @GetMapping("/ad/{id}")
    public ResponseEntity<Ad> getAdById(@PathVariable Long id){
        Ad ad = adService.getAdById(id);
        if(ad != null)
            return ResponseEntity.ok(ad);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getAllCities(){
        List<City> cities = cityService.getCities();
        return ResponseEntity.ok(cities);
    }
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }
}
