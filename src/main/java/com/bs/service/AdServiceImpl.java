package com.bs.service;

import com.bs.model.Ad;
import com.bs.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdServiceImpl implements AdService{
    private final AdRepository adRepository;
    @Autowired
    public AdServiceImpl(AdRepository adRepository) {
        this.adRepository = adRepository;
    }
    @Override
    public Ad addAd(Ad ad) {
        return adRepository.save(ad);
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
