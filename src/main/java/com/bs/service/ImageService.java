package com.bs.service;

import com.bs.model.Image;
import org.springframework.stereotype.Service;

@Service
public interface ImageService {
    Image getImageById(Long id);
    Image updateById(Long id, Long idAd, Image image);
}
