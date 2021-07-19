package com.jhaanuj.urlShortening.repository;

import com.jhaanuj.urlShortening.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Long> {

    List<Url> findAll();
}
