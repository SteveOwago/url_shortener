package com.moringaschool.url_qr_shortener.url_management;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class UrlRedirectController {
    private final UrlService urlService;

    public UrlRedirectController(UrlService urlService) {
        this.urlService = urlService;
    }

    // This handles requests like: yourdomain.com/AbCd12
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        // Get URL and increment hit count in one operation
        Url url = urlService.getAndIncrementByShortCode(shortCode);

        // Perform 302 temporary redirect to original URL
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url.getOriginalUrl()))
                .build();
    }
}
