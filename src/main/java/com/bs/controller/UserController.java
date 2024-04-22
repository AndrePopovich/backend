package com.bs.controller;

import com.bs.model.*;
import com.bs.payload.response.ProfileResponse;
import com.bs.service.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final AdService adService;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final ImageService imageService;
    public UserController(UserService userService,
                          AdService adService,
                          CategoryService categoryService,
                          CityService cityService,
                          ImageService imageService){
        this.userService = userService;
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
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        if(user != null)
            return ResponseEntity.ok(user);
        else
            return ResponseEntity.notFound().build();
    }


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
    @GetMapping("/ads-images/{idAd}")
    public ResponseEntity<List<Image>> getImagesByAdId(@PathVariable Long idAd){
        List<Image> images = adService.getImagesForAd(idAd);
        if(!images.isEmpty()){
            System.out.println("Кількість фото вже прямує по контролеру: " + images.size());
            return ResponseEntity.ok(images);}
        else{
            return ResponseEntity.notFound().build();}
    }

    @GetMapping("/ads")
    public ResponseEntity<List<Ad>> getAllAds(){
        List<Ad> ads = adService.getAds();
        return ResponseEntity.ok(ads);
    }


    @PostMapping("/ads")
    public ResponseEntity<Ad> addAd(@RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("user") Long userId,
                                    @RequestParam("category") Long categoryId,
                                    @RequestParam("city") Long cityId,
                                    @RequestParam("price") BigDecimal price,
                                    @RequestParam("views") int views,
                                    @RequestParam("file1") MultipartFile file1,
                                    @RequestParam(value = "file2", required = false) MultipartFile file2,
                                    @RequestParam(value = "file3", required = false) MultipartFile file3,
                                    @RequestParam(value = "file4", required = false) MultipartFile file4) throws IOException {
        List<MultipartFile> files = new ArrayList<>();

        if (file1 != null) {
            files.add(file1);
        }
        if (file2 != null) {
            files.add(file2);
        }
        if (file3 != null) {
            files.add(file3);
        }
        if (file4 != null) {
            files.add(file4);
        }
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
        Ad createdAd = adService.addAd(ad, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAd);
    }



    @PutMapping("/ads/{id}")
    public ResponseEntity<Ad> updateAd(@PathVariable Long id,
                                       @RequestParam("name") String name,
                                       @RequestParam("description") String description,
                                       @RequestParam("user") Long userId,
                                       @RequestParam("category") Long categoryId,
                                       @RequestParam("city") Long cityId,
                                       @RequestParam("price") BigDecimal price,
                                       @RequestParam("views") int views,
                                       @RequestParam("file1") MultipartFile file1,
                                       @RequestParam(value = "file2", required = false) MultipartFile file2,
                                       @RequestParam(value = "file3", required = false) MultipartFile file3,
                                       @RequestParam(value = "file4", required = false) MultipartFile file4) throws IOException {
        List<MultipartFile> files = new ArrayList<>();

        if (file1 != null) {
            files.add(file1);
        }
        if (file2 != null) {
            files.add(file2);
        }
        if (file3 != null) {
            files.add(file3);
        }
        if (file4 != null) {
            files.add(file4);
        }
        Ad existingAd = adService.getAdById(id);
        if(existingAd != null){
            User user = new User();
            user.setId(userId);
            Category category = new Category();
            category.setId(categoryId);
            City city = new City();
            city.setId(cityId);

            existingAd.setName(name);
            existingAd.setDescription(description);
            existingAd.setUser(user);
            existingAd.setCategory(category);
            existingAd.setCity(city);
            existingAd.setPrice(price);
            existingAd.setViews(views);

            Ad updatedAd = adService.updateAd(existingAd, files);
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
