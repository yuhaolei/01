package com.one.eng.bean;

public class EngBean {
    private String name;
    private int imageId;
    private String lession_url;

    public EngBean(String name, int imageId,
                   String lession_url) {
        this.name = name;
        this.imageId = imageId;
        this.lession_url = lession_url;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getLession_url() {
        return lession_url;
    }

    public void setLession_url(String lession_url) {
        this.lession_url = lession_url;
    }
}
