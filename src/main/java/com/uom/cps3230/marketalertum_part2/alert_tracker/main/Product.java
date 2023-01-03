package com.uom.cps3230.marketalertum_part2.alert_tracker.main;

public class Product {
    // product separated to more easily mock/have test doubles of this class
    protected int alertType;
    protected String heading;
    protected String description;
    protected String url;
    protected String imageUrl;

    protected int priceInCents;

    // constructor injection
    public Product(int alertType, String heading, String description, String url, String imageUrl, int priceInCents) {
        this.alertType = alertType;
        this.heading = heading;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.priceInCents = priceInCents;
    }

    // default constructor
//    public Product() {
//    }

    // getters
    public int getAlertType() {
        return alertType;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPriceInCents() {
        return priceInCents;
    }

    //setters
//    public void setAlertType(int alertType) {
//        this.alertType = alertType;
//    }
//
//    public void setHeading(String heading) {
//        this.heading = heading;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//
//    public void setPriceInCents(int priceInCents) {
//        this.priceInCents = priceInCents;
//    }
}
