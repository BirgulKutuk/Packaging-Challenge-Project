package com.mobiquity.util;

import com.mobiquity.util.Item;

import java.util.List;

public class Package {

    private Double packageLimit;
    private List<Item> packageItems;

    public Double getPackageLimit() {
        return packageLimit;
    }

    public void setPackageLimit(Double packageLimit) {
        this.packageLimit = packageLimit;
    }

    public List<Item> getPackageItems() {
        return packageItems;
    }

    public void setPackageItems(List<Item> packageItems) {
        this.packageItems = packageItems;
    }
}
