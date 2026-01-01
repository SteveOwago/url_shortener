package com.moringaschool.url_qr_shortener.url_management;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Integer> {
    boolean existsByOriginalUrl(String originalUrl);

    boolean existsByShortUrl(String shortUrl);
}
