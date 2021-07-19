package com.jhaanuj.urlShortening.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description = "Request object for POST method")
public class UrlLongRequest {
    @ApiModelProperty(required = true, notes = "Service to convert to short URL")
    private String longUrl;


    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

}
