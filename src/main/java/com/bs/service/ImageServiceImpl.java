package com.bs.service;

import com.bs.model.Ad;
import com.bs.model.Image;
import com.bs.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
@Service
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;
    @Autowired
    public ImageServiceImpl (ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public Image updateById(Long id, Long idAd, Image image) {
        image.setId(id);
        Ad ad = new Ad();
        ad.setId(idAd);
        image.setAd(ad);
        return imageRepository.save(image);
    }

}
