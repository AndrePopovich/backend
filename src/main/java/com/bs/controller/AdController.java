package com.bs.controller;

import com.bs.model.Ad;
import com.bs.service.AdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
public class AdController {
    private final AdService adService;

    public AdController(AdService adService){
        this.adService = adService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ad> getAdById(@PathVariable Long id){
        Ad ad = adService.getAdById(id);
        if(ad != null)
            return ResponseEntity.ok(ad);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Ad>> getAllAds(){
        List<Ad> ads = adService.getAds();
        return ResponseEntity.ok(ads);
    }

    @PostMapping
    public ResponseEntity<Ad> addAd(@RequestBody Ad ad){
        Ad createdAd = adService.addAd(ad);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAd);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ad> updateAd(@PathVariable Long id, @RequestBody Ad ad){
        Ad existingAd = adService.getAdById(id);
        if(existingAd != null){
            ad.setId(id);
            Ad updatedAd = adService.updateAd(ad);
            return ResponseEntity.ok(updatedAd);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable Long id){
        Ad existingAd = adService.getAdById(id);
        if(existingAd != null){
            adService.deleteAd(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
