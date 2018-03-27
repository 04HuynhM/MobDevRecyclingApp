package com.example.martin.recyclingapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
    private String dateScanned;
    @ColumnInfo(name = "category")
    private String productCategory;
    @ColumnInfo
    private String imageUrl;

    public Item(@NonNull String uid,
                String barcodeNumber,
                String productName,
                String productMaterial,
                String dateScanned,
                String category,
                @Nullable String imageUrl) {
        this.uid = uid;
        this.barcodeNumber = barcodeNumber;
        this.productName = productName;
        this.productMaterial = productMaterial;
        this.dateScanned = dateScanned;
        this.productCategory = category;
        this.imageUrl = imageUrl;
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

    public String getDateScanned() {
        return dateScanned;
    }

    public void setDateScanned(String dateScanned) {
        this.dateScanned = dateScanned;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setProductCategory(String category) {
        this.productCategory = category;
    }
}
