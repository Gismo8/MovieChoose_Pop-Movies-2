package com.gismo.moviechoose.model;

import java.io.Serializable;

/**
 * Created by gismo on 2018. 04. 29..
 */

public class ReviewObject implements Serializable {

    String id;
    String author;
    String content;
    String url;

    public ReviewObject(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public ReviewObject(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
