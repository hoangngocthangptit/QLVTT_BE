package com.example.blogchipo.entityMX;
public class Info {
    private String canBookCount;
    private String price;
    private String specAttrs;


    public String getCanBookCount() {
        return canBookCount;
    }

    public Info() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpecAttrs() {
        return specAttrs;
    }

    public void setSpecAttrs(String specAttrs) {
        this.specAttrs = specAttrs;
    }

    public void setCanBookCount(String canBookCount) {
        this.canBookCount = canBookCount;
    }

    public Info(String quantity, String price, String type) {
        this.canBookCount = quantity;
        this.price = price;
        this.specAttrs = type;
    }
}
