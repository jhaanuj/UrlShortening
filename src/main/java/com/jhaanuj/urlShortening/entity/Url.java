package com.jhaanuj.urlShortening.entity;

import javax.persistence.*;
import java.util.Date;


@Table(name="url")
@Entity
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String longUrl;


    @Column(nullable = false)
    private Date createdDate;

    @Column(name="hash")
    private String hash;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name="hits")
    private int hits;
    public void setHits(int hits){
        this.hits=hits;
    }

    public  int getHits(){
        return hits;
    }
    public void increaseHits(){
        this.hits+=1;
    }

    public String getHash(){
        return hash;
    }
    public void setHash(String hash){
        this.hash= hash;
    }

}