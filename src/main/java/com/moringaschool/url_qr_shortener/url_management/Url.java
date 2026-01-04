package com.moringaschool.url_qr_shortener.url_management;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.util.Objects;

@Entity
@Table(name = "url", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"originalUrl", "shortCode"})
})
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @URL(message = "Please provide a valid URL")
    @NotBlank(message = "Website URL is required")
    @Size(min = 3, max = 500, message = "URL must be between 3 and 500 characters")
    @Column(nullable = false, unique = true, length = 500)
    private String originalUrl;

    @ReadOnlyProperty
    @Column(nullable = false, unique = true, length = 20)
    private String shortCode;  // Just "AbCd12"

    @Column(nullable = false)
    private Integer hitCount = 0;  // Initialize to 0

    // Constructors
    public Url() {}

    public Url(Integer id, String originalUrl, String shortCode, Integer hitCount) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.hitCount = hitCount;
    }

    // Getters and Setters
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

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public Integer getHitCount() {
        return hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }

    // Helper method to get full short URL
    @Transient
    public String getFullShortUrl(String baseUrl) {
        return baseUrl + "/" + shortCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Url that = (Url) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(originalUrl, that.originalUrl) &&
                Objects.equals(shortCode, that.shortCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originalUrl, shortCode);
    }
}