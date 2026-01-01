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
        url.setShortUrl(this.generateShortUrl());
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

    public String generateShortUrl() {
        // Implement a method to generate a unique short URL
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder shortUrl = new StringBuilder();
        String protocol = "https://mrsX";
        // Generate a 6-character random string
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * characters.length());
            shortUrl.append(characters.charAt(index));
        }
        String finalShortUrl = protocol + shortUrl.toString();
        if (this.IsShortUrlAvailable(finalShortUrl)) {
            return generateShortUrl();
        }
        return finalShortUrl;
    }

    @ReadOnlyProperty
    public Boolean IsShortUrlAvailable(String shortUrl) {
        return (Boolean) urlRepository.existsByShortUrl(shortUrl);
    }

}
