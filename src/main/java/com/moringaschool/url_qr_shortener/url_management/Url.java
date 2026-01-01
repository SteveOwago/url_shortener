package com.moringaschool.url_qr_shortener.url_management;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.util.Objects;

@Entity
@Table(name = "url", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"originalUrl"}),
        @UniqueConstraint(columnNames = {"shortUrl"})
})

public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     private Integer id;
    @URL(message = "Please provide a valid URL")
    @NotBlank(message = "Website URL is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    @Column(nullable = false, unique = true, length = 500)
    private String originalUrl;

    @URL(message = "Please provide a valid URL")
    @ReadOnlyProperty
    @Column(nullable = false, unique = true, length = 500)
     private String shortUrl;
     public Integer hitCount;

    public Url(Integer id, String originalUrl, String shortUrl, Integer hitCount) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.hitCount = hitCount;
    }

    public Url() {

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Url that = (Url) o;
        return Objects.equals(id, that.id) && Objects.equals(originalUrl, that.originalUrl) && Objects.equals(shortUrl, that.shortUrl) && Objects.equals(hitCount, that.hitCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originalUrl, shortUrl, hitCount);
    }
}
