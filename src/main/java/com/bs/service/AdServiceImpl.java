package com.bs.service;

import com.bs.model.Ad;
import com.bs.model.Image;
import com.bs.repository.AdRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdServiceImpl implements AdService{
    private final AdRepository adRepository;
    private final ImageService imageService;

    @Autowired
    public AdServiceImpl(AdRepository adRepository, ImageService imageService) {
        this.adRepository = adRepository;
        this.imageService = imageService;
    }
    @Override
    public List<Ad> searchAds(String adName, String categoryName, String cityName, BigDecimal minPrice, BigDecimal maxPrice, String sortBy) {
        List<Ad> ads;
        if ((adName == null || adName.isEmpty()) &&
                (categoryName == null || categoryName.isEmpty()) &&
                (cityName == null || cityName.isEmpty()) &&
                minPrice == null &&
                maxPrice == null) {
            return adRepository.findRandomAds(50);
        }
        if(adName != null && !adName.isEmpty()){
            ads = adRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(adName, adName);
        }
        else {
            ads = adRepository.findAll();
        }
        // Фільтрація за категорією, якщо задано
        if (categoryName != null && !categoryName.isEmpty()) {
            ads = ads.stream()
                    .filter(ad -> ad.getCategory().getName().equalsIgnoreCase(categoryName))
                    .collect(Collectors.toList());
        }

        // Фільтрація за містом, якщо задано
        if (cityName != null && !cityName.isEmpty()) {
            ads = ads.stream()
                    .filter(ad -> ad.getCity().getName().equalsIgnoreCase(cityName))
                    .collect(Collectors.toList());
        }

        // Фільтрація за мінімальною ціною, якщо задано
        if (minPrice != null) {
            ads = ads.stream()
                    .filter(ad -> ad.getPrice().compareTo(minPrice) >= 0)
                    .collect(Collectors.toList());
        }

        // Фільтрація за максимальною ціною, якщо задано
        if (maxPrice != null) {
            ads = ads.stream()
                    .filter(ad -> ad.getPrice().compareTo(maxPrice) <= 0)
                    .collect(Collectors.toList());
        }

        // Сортуємо результати, якщо необхідно
        if (sortBy != null) {
            switch (sortBy) {
                case "priceAsc":
                    ads.sort(Comparator.comparing(Ad::getPrice));
                    break;
                case "priceDesc":
                    ads.sort((a1, a2) -> a2.getPrice().compareTo(a1.getPrice()));
                    break;
                case "dateAsc":
                    ads.sort(Comparator.comparing(Ad::getDateOfCreated));
                    break;
                case "dateDesc":
                    ads.sort((a1, a2) -> a2.getDateOfCreated().compareTo(a1.getDateOfCreated()));
                    break;
                default:
                    // за замовчуванням сортуємо за ID
                    ads.sort(Comparator.comparingLong(Ad::getId));
                    break;
            }
        }

        return ads;
    }


    @Override
    public Ad addAd(Ad ad, List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                Image image = toImageEntity(file);
                ad.addImageToAd(image);
                if(file.getName().equals("file1")){
                    image.setPreviewImage(true);
                }

            }
        }

        Ad createdAd = adRepository.save(ad);
        createdAd.setPreviewImageId(createdAd.getImages().get(0).getId());

        return adRepository.save(ad);
    }


    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    @Override
    @Transactional
    public Ad updateAd(Ad ad, List<MultipartFile> files) throws IOException {
        int count = 0;
        List<Image> images = ad.getImages();
        for(Image image : images){
            if(files.get(0).isEmpty()){
                continue;
            }
            imageService.updateById(image.getId(), ad.getId(), toImageEntity(files.get(0)));
            files.remove(0);
            count++;
        }
        if(images.size() < (count + 1)){
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                   Image image = toImageEntity(file);
                    ad.addImageToAd(image);
                }
            }
        }


        Ad createdAd = adRepository.save(ad);
        createdAd.setPreviewImageId(createdAd.getImages().get(0).getId());
        return adRepository.save(ad);
    }

    @Override
    public Ad getAdById(Long id) {
        return adRepository.findById(id).orElse(null);
    }

    @Override
    public List<Ad> getAds() {
        return adRepository.findAll();
    }

    @Override
    public void deleteAd(Long id) {
        adRepository.deleteById(id);
    }
    @Override
    @Transactional
    public List<Image> getImagesForAd(long adId) {
        Ad ad = adRepository.findById(adId).orElse(null);

        if (ad != null) {
            System.out.println("Кількість фото вже прямує по сервісу: " + ad.getImages().size());
            return ad.getImages();
        } else {
            return Collections.emptyList(); // повертаємо порожній список, якщо оголошення з таким id не знайдено
        }
    }
    @Override
    @Transactional
    public Image getPreviewImageForAd(long adId) {
        Ad ad = adRepository.findById(adId).orElse(null);
        if (ad != null) {
            Long idPreviewImage = ad.getPreviewImageId();
            return imageService.getImageById(idPreviewImage);
        } else {
            return null;
        }
    }
}
