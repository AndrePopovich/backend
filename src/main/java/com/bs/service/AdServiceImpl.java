package com.bs.service;

import com.bs.model.Ad;
import com.bs.model.Image;
import com.bs.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdServiceImpl implements AdService{
    private final AdRepository adRepository;
    @Autowired
    public AdServiceImpl(AdRepository adRepository) {
        this.adRepository = adRepository;
    }
    @Override
    public Ad addAd(Ad ad, MultipartFile file) throws IOException {
        Image image1;
            if(file.getSize() != 0){
                image1 = toImageEntity(file);
                ad.addImageToAd(image1);
                image1.setPreviewImage(true);
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
    public Ad updateAd(Ad ad) {
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
}
