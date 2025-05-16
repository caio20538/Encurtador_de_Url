package com.url.UrlShortener.service;

import com.url.UrlShortener.controller.dto.ShortenUrlRequest;
import com.url.UrlShortener.entites.UrlEntity;
import com.url.UrlShortener.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String getShortenUrl(ShortenUrlRequest request, HttpServletRequest servletRequest) {
        var redirectUrl = getString(request, servletRequest);
        return redirectUrl;
    }

    private String getString(ShortenUrlRequest request, HttpServletRequest servletRequest) {
        String id;
        do {
            id = RandomStringUtils.randomAlphanumeric(5, 10);
        } while (urlRepository.existsById(id));

        urlRepository.save(new UrlEntity(id, request.url(), LocalDateTime.now().plusMinutes(1)));

        var redirectUrl = servletRequest.getRequestURL().toString().replace("shorten-url", id);
        return redirectUrl;
    }
}
