package com.example.martin.recyclingapp.db;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by charlie on 2018-03-11.
 */

public class User {

    public static final int GENDER_MALE = 0;
    public static final int GENDER_FEMALE = 1;
    public static final int GENDER_OTHER = 2;

    private String uid;
    private String name;
    private String surname;
    private String email;
    private int age;
    private int gender;
    private String searchedItems;

    public User(@NonNull String uid,
                String name,
                String surname,
                String email,
                int age,
                int gender,
                @Nullable String searchedItems) {

        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.searchedItems = searchedItems;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getSearchedItems() {
        return searchedItems;
    }

    public void setSearchedItems(String searchedItems) {
        this.searchedItems = searchedItems;
    }
}
