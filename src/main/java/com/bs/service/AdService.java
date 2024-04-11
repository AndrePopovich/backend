package com.bs.service;

import com.bs.model.Ad;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface AdService {
    public Ad addAd(Ad ad, MultipartFile file) throws IOException;
    public Ad updateAd(Ad ad);
    public Ad getAdById(Long id);
    public List<Ad> getAds();
    public void deleteAd(Long id);
}
