package com.moringaschool.url_qr_shortener.url_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/urls")
public class UrlController {
    protected final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping
    public ResponseEntity<List<Url>> findAll() {
        List<Url> urls = urlService.getAllUrls();
        return ResponseEntity.ok(urls);
    }

    @PostMapping
    public ResponseEntity<Url> save(@RequestBody Url url) {
        Url $url = urlService.createUrl(url);
        return ResponseEntity.ok($url);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Url> update(@PathVariable Integer id, @RequestBody Url url) {
        Url $url = urlService.updateUrl(id, url);
        return ResponseEntity.ok($url);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        urlService.deleteUrl(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Url> findById(@PathVariable Integer id) {
        Url url = urlService.getUrlById(id);
        return ResponseEntity.ok(url);
    }
}
