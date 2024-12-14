package com.example.backend.entity;

public class Commercial {
    private Integer id;
    private String price;
    private String title;
    private String url;
    private String imgUrl;
    private String shop;
    private String shopUrl;
    private String platform;

    public Commercial(Integer id, String price, String title, String url, String imgUrl, String shop, String shopUrl, String platform) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.url = url;
        this.imgUrl = imgUrl;
        this.shop = shop;
        this.shopUrl = shopUrl;
        this.platform = platform;
    }


    public Commercial() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
