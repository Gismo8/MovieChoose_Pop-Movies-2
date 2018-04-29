package com.gismo.moviechoose.enums;

/**
 * Created by gismo on 2018. 03. 11..
 */

public enum SortingOptions {
    TOP_RATED("top_rated"), MOST_POPULAR("popular");

    String path;

    SortingOptions(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
