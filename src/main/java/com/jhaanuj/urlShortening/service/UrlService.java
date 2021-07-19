package com.jhaanuj.urlShortening.service;

import com.jhaanuj.urlShortening.dto.UrlLongRequest;
import com.jhaanuj.urlShortening.entity.Url;
import com.jhaanuj.urlShortening.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UrlService {
    static Logger log = LoggerFactory.getLogger(UrlService.class);
    private final UrlRepository urlRepository;
    private final HashAlgo conversion;

    public UrlService(UrlRepository urlRepository, HashAlgo hashAlgo) {
        this.urlRepository = urlRepository;
        this.conversion = hashAlgo;
    }

    public String convertToShortUrl(UrlLongRequest request) {
        Url url = new Url();
        url.setLongUrl(request.getLongUrl());
        url.setCreatedDate(new Date());
        url.setHits(00);
        Url entity = urlRepository.save(url);
        String hash = conversion.encode((entity.getId()));
        url.setHash(hash);
        urlRepository.save(url);
        return hash;
    }

    public String getOriginalUrl(String shortUrl) {
        long id = conversion.decode(shortUrl);
        Url entity = urlRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no entity with " + shortUrl));


        entity.increaseHits();
        urlRepository.save(entity);
        log.info(" value of hits is :" + entity.getHits());
        return entity.getLongUrl();
    }

    public int getHitCount(String shortUrl) {
        long id = conversion.decode(shortUrl);
        Url entity = urlRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No entity Found with " + shortUrl));
        return entity.getHits();
    }


    public List<String> shortUrlList() {
        List<Url> url_List = urlRepository.findAll();
        List<String> shortUrlList = new ArrayList<>();
        for (Url url : url_List) {
            shortUrlList.add(url.getHash());
        }
        return shortUrlList;

    }


}
