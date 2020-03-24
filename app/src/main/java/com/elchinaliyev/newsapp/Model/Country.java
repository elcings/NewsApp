package com.elchinaliyev.newsapp.Model;

import androidx.annotation.NonNull;

public class Country {
    String name;
    String shortName;

    public Country(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
