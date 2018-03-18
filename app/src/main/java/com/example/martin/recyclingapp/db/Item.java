package com.example.martin.recyclingapp.db;

import java.util.Date;

/**
 * Created by charlie on 2018-03-10.
 */

public class Item {
    private String title;
    private String description;
    private String productName;
    private String barcodeNumber;
    private String productMaterial;
    private Date dateScanned;

    public Item(String barcodeNumber, String productName, String productMaterial, Date dateScanned){
        this.barcodeNumber = barcodeNumber;
        this.productName = productName;
        this.productMaterial = productMaterial;
        this.dateScanned = dateScanned;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getProductName() {
        return productName;
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    public String getProductMaterial() {
        return productMaterial;
    }

    public Date getDateScanned() {
        return dateScanned;
    }
}
