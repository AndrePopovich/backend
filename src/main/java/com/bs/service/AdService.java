package com.bs.service;

import com.bs.model.Ad;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdService {
    public Ad addAd(Ad ad);
    public Ad updateAd(Ad ad);
    public Ad getAdById(Long id);
    public List<Ad> getAds();
    public void deleteAd(Long id);
}
