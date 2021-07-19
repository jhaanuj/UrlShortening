package com.jhaanuj.urlShortening.service;

import com.jhaanuj.urlShortening.dto.UrlLongRequest;
import com.jhaanuj.urlShortening.entity.Url;
import com.jhaanuj.urlShortening.repository.UrlRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTest {
    @Mock
    UrlRepository mockUrlRepository;

    @Mock
    HashAlgo mockHashAlgo;

    @InjectMocks
    UrlService urlService;

    @Test
    public void convertToShortUrlTest() {
        Url url = new Url();
        url.setLongUrl("https://github.com/jhaanuj/UrlShortening");

        url.setCreatedDate(new Date());
        url.setId(2);

        when(mockUrlRepository.save(any(Url.class))).thenReturn(url);
        when(mockHashAlgo.encode(url.getId())).thenReturn("b");

        UrlLongRequest urlRequest = new UrlLongRequest();
        urlRequest.setLongUrl("https://github.com/jhaanuj/UrlShortening");

        assertEquals("b", urlService.convertToShortUrl(urlRequest));
    }

    @Test
    public void getOriginalUrlTest() {
        when(mockHashAlgo.decode("b")).thenReturn((long) 2);

        Url url = new Url();
        url.setLongUrl("https://github.com/jhaanuj/UrlShortening");
        url.setCreatedDate(new Date());
        url.setId(2);

        when(mockUrlRepository.findById((long) 2)).thenReturn(java.util.Optional.of(url));
        assertEquals("https://github.com/jhaanuj/UrlShortening", urlService.getOriginalUrl("b"));

    }
}
