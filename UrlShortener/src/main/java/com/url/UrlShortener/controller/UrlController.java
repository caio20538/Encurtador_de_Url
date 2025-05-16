package com.url.UrlShortener.controller;

import com.url.UrlShortener.controller.dto.ShortenUrlResponse;
import com.url.UrlShortener.controller.dto.ShortenUrlRequest;

import com.url.UrlShortener.entites.UrlEntity;
import com.url.UrlShortener.repository.UrlRepository;
import com.url.UrlShortener.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.time.LocalDateTime;

@RestController
public class UrlController {

    private final UrlRepository urlRepository;
    private final UrlService urlService;

    public UrlController(UrlRepository urlRepository, UrlService urlService) {
        this.urlRepository = urlRepository;
        this.urlService = urlService;
    }

    @PostMapping(value = "/shorten-url")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@RequestBody ShortenUrlRequest request,
                                                         HttpServletRequest servletRequest) {
        var redirectUrl = urlService.getShortenUrl(request, servletRequest);

        return ResponseEntity.ok(new ShortenUrlResponse(redirectUrl));
    }



    @GetMapping("{id}")
    public ResponseEntity<Void> redirect(@PathVariable("id") String id) {

        var url = urlRepository.findById(id);

        if (url.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url.get().getFullUrl()));

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }

}
