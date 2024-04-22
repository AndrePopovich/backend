package com.bs.service;

import com.bs.model.Ad;
import com.bs.model.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public interface AdService {
    List<Ad> searchAds(String adName, String categoryName, String cityName, BigDecimal minPrice, BigDecimal maxPrice, String sortBy);
    Ad addAd(Ad ad, List<MultipartFile> files) throws IOException;
    Ad updateAd(Ad ad, List<MultipartFile> files) throws IOException;
    Ad getAdById(Long id);
    List<Ad> getAds();
    void deleteAd(Long id);
    List<Image> getImagesForAd(long adId);
    Image getPreviewImageForAd(long adId);
}
