package com.moringaschool.url_qr_shortener.url_management;

import com.moringaschool.url_qr_shortener.exceptionhandlers.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlService {
//    All business logic will be implemented here
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    //Crud operations create, read, update, delete
    //getAllUrls, getUrlById, createUrl, updateUrl, deleteUrl
    @ReadOnlyProperty
    public List<Url> getAllUrls() {
        return urlRepository.findAll();
    }

    public Url createUrl(@Valid Url url) {
        if (urlRepository.existsByOriginalUrl(url.getOriginalUrl())) {
            throw new DuplicateKeyException("Url already exists");
        }
        url.setShortCode(this.generateShortCode());
        return urlRepository.save(url);
    }

    public void deleteUrl(Integer id) {
        //Check if URL exists before deleting
        Url existingUrl = urlRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Url with id " + id + " not found"));
        urlRepository.deleteById(existingUrl.getId());
    }

    @ReadOnlyProperty
    public Url getUrlById(Integer id) {
        return urlRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Url with id " + id + " not found"));
    }

    public Url updateUrl(Integer id, Url url) {
        Url existingUrl = urlRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Url with id " + id + " not found"));
        if (existingUrl != null) {
            existingUrl.setOriginalUrl(url.getOriginalUrl());
            existingUrl.setHitCount(url.getHitCount());
            return urlRepository.save(existingUrl);
        }
        return null;
    }

    public Url incrementHitCount(Integer id) {
        Url existingUrl = urlRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Url with id " + id + " not found"));
        if (existingUrl != null) {
            existingUrl.setHitCount(existingUrl.getHitCount() + 1);
            return urlRepository.save(existingUrl);
        }
        return null;
    }

    // Method to generate a unique short URL
    public String generateShortCode() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder shortCode = new StringBuilder();

        // Generate a 6-character random string
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * characters.length());
            shortCode.append(characters.charAt(index));
        }

        String code = shortCode.toString();

        // Check if code already exists, regenerate if it does
        if (urlRepository.existsByShortCode(code)) {
            return generateShortCode();
        }

        return code;
    }

    // New method to get URL by short code and increment hit count
    public Url getAndIncrementByShortCode(String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new ResourceNotFoundException("Short code " + shortCode + " not found"));

        url.setHitCount(url.getHitCount() + 1);
        return urlRepository.save(url);
    }

    @ReadOnlyProperty
    public Boolean IsShortUrlAvailable(String shortCode) {
        return (Boolean) urlRepository.existsByShortCode(shortCode);
    }

}
