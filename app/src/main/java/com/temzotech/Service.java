package com.temzotech;

import java.io.Serializable;

public class Service implements Serializable {

    private String name;
    private int icon;
    private String description;
    private String category;

    public Service(String name, int icon, String description, String category) {
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }
}