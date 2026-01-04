package com.moringaschool.url_qr_shortener.url_management;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Integer> {
    boolean existsByOriginalUrl(String originalUrl);

    boolean existsByShortCode(String code);

    Optional<Url> findByShortCode(String shortCode);

}
