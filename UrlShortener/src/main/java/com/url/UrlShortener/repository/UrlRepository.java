package com.url.UrlShortener.repository;

import com.url.UrlShortener.entites.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<UrlEntity, String> {
}
