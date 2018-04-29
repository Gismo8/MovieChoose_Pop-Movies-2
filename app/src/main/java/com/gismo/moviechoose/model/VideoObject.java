package com.gismo.moviechoose.model;

import java.io.Serializable;

/**
 * Created by gismo on 2018. 04. 29..
 */

public class VideoObject implements Serializable {

    String site;
    int id;
    String iso_639_1;
    String name;
    String type;
    String key;
    String iso_3166_1;
    String size;

    public VideoObject(String site, int id, String iso_639_1, String name, String type, String key, String iso_3166_1, String size) {
        this.site = site;
        this.id = id;
        this.iso_639_1 = iso_639_1;
        this.name = name;
        this.type = type;
        this.key = key;
        this.iso_3166_1 = iso_3166_1;
        this.size = size;
    }

    public VideoObject(int id, String name, String key) {
        this.id = id;
        this.name = name;
        this.key = key;
        //this.size = size;
    }

    public VideoObject(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public int getId() {
        return id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "ClassVideoObject [site = " + site + ", id = " + id + ", iso_639_1 = " + iso_639_1 + ", name = " + name + ", type = " + type + ", key = " + key + ", iso_3166_1 = " + iso_3166_1 + ", size = " + size + "]";
    }
}
