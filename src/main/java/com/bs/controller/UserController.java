package com.bs.controller;

import com.bs.model.*;
import com.bs.payload.response.ProfileResponse;
import com.bs.service.AdService;
import com.bs.service.CategoryService;
import com.bs.service.CityService;
import com.bs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final AdService adService;
    private final CategoryService categoryService;
    private final CityService cityService;
    public UserController(UserService userService, AdService adService, CategoryService categoryService, CityService cityService){
        this.userService = userService;
        this.adService = adService;
        this.categoryService = categoryService;
        this.cityService = cityService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        if(user != null)
            return ResponseEntity.ok(user);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }/*
    @Deprecated
    @GetMapping("user")
    public ResponseEntity<User> getUserByEmail(@RequestBody String email){
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
*/
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.getUserByEmail(username);
        ProfileResponse profile = new ProfileResponse(
                user.get().getId(),
                user.get().getFirstname(),
                user.get().getLastname(),
                username,
                user.get().getRole(),
                user.get().getPhone(),
                user.get().getDateOfRegistration(),
                user.get().getAds());
        return ResponseEntity.ok(profile);

    }
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        User createdUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        User existingUser = userService.getUserById(id);
        if(existingUser != null){
            user.setId(id);
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        User existingUser = userService.getUserById(id);
        if(existingUser != null){
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/ads/{id}")
    public ResponseEntity<Ad> getAdById(@PathVariable Long id){
        Ad ad = adService.getAdById(id);
        if(ad != null)
            return ResponseEntity.ok(ad);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/ads")
    public ResponseEntity<List<Ad>> getAllAds(){
        List<Ad> ads = adService.getAds();
        return ResponseEntity.ok(ads);
    }

    /*@GetMapping("/myads")
    public ResponseEntity<List<Ad>> getMyAds(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.getUserByEmail(username);
        ProfileResponse profile = new ProfileResponse(
                user.get().getId(),
                user.get().getFirstname(),
                user.get().getLastname(),
                username,
                user.get().getRole(),
                user.get().getPhone(),
                user.get().getDateOfRegistration(),
                user.get().getAds());
        return ResponseEntity.ok(profile);
    }*/
    /*
     @PostMapping("/ads")
    public ResponseEntity<Ad> addAd(@RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("user") Long userId,
                                    @RequestParam("category") Long categoryId,
                                    @RequestParam("city") Long cityId,
                                    @RequestParam("price") BigDecimal price,
                                    @RequestParam("views") int views,
                                    @RequestParam("file") MultipartFile file) throws IOException {
        Ad ad = new Ad();
        User user = new User();
        user.setId(userId);
        Category category = new Category();
        category.setId(categoryId);
        City city = new City();
        city.setId(cityId);
        ad.setName(name);
        ad.setDescription(description);
        ad.setUser(user);
        ad.setCategory(category);
        ad.setCity(city);
        ad.setPrice(price);
        ad.setViews(views);
        MultipartFile file = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
        Ad createdAd = adService.addAd(ad, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAd);
    }*/
    @PostMapping("/ads")
    public ResponseEntity<Ad> addAd(@RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("user") Long userId,
                                    @RequestParam("category") Long categoryId,
                                    @RequestParam("city") Long cityId,
                                    @RequestParam("price") BigDecimal price,
                                    @RequestParam("views") int views,
                                    @RequestParam("file") MultipartFile file) throws IOException {
        Ad ad = new Ad();
        User user = new User();
        user.setId(userId);
        Category category = new Category();
        category.setId(categoryId);
        City city = new City();
        city.setId(cityId);
        ad.setName(name);
        ad.setDescription(description);
        ad.setUser(user);
        ad.setCategory(category);
        ad.setCity(city);
        ad.setPrice(price);
        ad.setViews(views);
        ad.setActive(true);
        Ad createdAd = adService.addAd(ad, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAd);
    }




    @PutMapping("/ads/{id}")
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

    @DeleteMapping("/ads/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable Long id){
        Ad existingAd = adService.getAdById(id);
        if(existingAd != null){
            adService.deleteAd(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ads/cities")
    public ResponseEntity<List<City>> getAllCities(){
        List<City> cities = cityService.getCities();
        return ResponseEntity.ok(cities);
    }
    @GetMapping("/ads/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

}
