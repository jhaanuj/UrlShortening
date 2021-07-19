package com.jhaanuj.urlShortening.controller;

import com.jhaanuj.urlShortening.dto.UrlLongRequest;
import com.jhaanuj.urlShortening.service.UrlService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
@RequestMapping("/api/v1")
public class UrlController {
    private AtomicInteger count = new AtomicInteger();
    Logger log = LoggerFactory.getLogger(UrlController.class);
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    /**
     * To create Short URL
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Convert new url", notes = "Converts long url to short url")
    @PostMapping("create-short")
    public String convertToShortUrl(@RequestBody UrlLongRequest request) {
        log.info("url to short is " + request.getLongUrl());
        return urlService.convertToShortUrl(request);
    }

    /**
     * To redirect url using short URL
     *
     * @param shortUrl
     * @return
     */
    @ApiOperation(value = "Redirect", notes = "Finds original url from short url and redirects")
    @GetMapping(value = "{shortUrl}")
    @Cacheable(value = "urls", key = "#shortUrl", sync = true)
    public ResponseEntity<Void> getAndRedirect(@PathVariable String shortUrl) {
        log.info(" short URL is  " + shortUrl);
        String url = urlService.getOriginalUrl(shortUrl);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }

    /**
     *  Total Hits
     * @param shortUrl
     * @return
     */
    @GetMapping("/count/{shortUrl}")
    public int getCount(@PathVariable String shortUrl) {
        return urlService.getHitCount(shortUrl);
    }

    /**
     *  To get the List of all shortUrl
     * @return
     */
    @GetMapping("/listAll")
    public List<String> listShortenUrl() {
        return urlService.shortUrlList();
    }
}
