package com.example.martin.recyclingapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by charlie on 2018-03-10.
 */

@Entity
public class Item {

    @PrimaryKey
    @NonNull
    private String uid;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "product_name")
    private String productName;
    @ColumnInfo(name = "barcode_number")
    private String barcodeNumber;
    @ColumnInfo(name = "product_material")
    private String productMaterial;
    @ColumnInfo(name = "date_scanned")
    private long dateScanned;
    @ColumnInfo(name = "category")
    private String category;

    public Item(@NonNull String uid,
                String barcodeNumber,
                String productName,
                String productMaterial,
                long dateScanned,
                String category) {
        this.uid = uid;
        this.barcodeNumber = barcodeNumber;
        this.productName = productName;
        this.productMaterial = productMaterial;
        this.dateScanned = dateScanned;
        this.category = category;
    }

    public Item() {
    }

    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public String getProductMaterial() {
        return productMaterial;
    }

    public void setProductMaterial(String productMaterial) {
        this.productMaterial = productMaterial;
    }

    public long getDateScanned() {
        return dateScanned;
    }

    public void setDateScanned(long dateScanned) {
        this.dateScanned = dateScanned;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
