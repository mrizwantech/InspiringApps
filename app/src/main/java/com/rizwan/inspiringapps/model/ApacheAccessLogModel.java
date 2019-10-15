package com.rizwan.inspiringapps.model;

import androidx.annotation.Nullable;

public class ApacheAccessLogModel {


    private String pages;
    private String idAddess;



    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getIdAddess() {
        return idAddess;
    }

    public void setIdAddess(String idAddess) {
        this.idAddess = idAddess;
    }

    public ApacheAccessLogModel(String pages, String idAddess) {
        this.pages = pages;
        this.idAddess = idAddess;
    }




}

