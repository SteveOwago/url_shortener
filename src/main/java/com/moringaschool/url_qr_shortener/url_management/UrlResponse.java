package com.moringaschool.url_qr_shortener.url_management;

public class UrlResponse {
    private Integer id;
    private String originalUrl;
    private String shortUrl;  // Full URL: "yourdomain.com/AbCd12"
    private Integer hitCount;

    public UrlResponse(Url url, String baseUrl) {
        this.id = url.getId();
        this.originalUrl = url.getOriginalUrl();
        this.shortUrl = baseUrl + "/" + url.getShortCode();
        this.hitCount = url.getHitCount();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Integer getHitCount() {
        return hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }
}