package com.gismo.moviechoose.enums;

/**
 * Created by gismo on 2018. 04. 02..
 */

public enum QueryTypes {

    VIDEOS("videos"),
    REVIEWS("reviews");

    String path;

    QueryTypes(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
