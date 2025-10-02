package com.temzotech;

import java.io.Serializable;

public class Service implements Serializable {

    private String name;
    private int icon;
    private String description;

    public Service(String name, int icon, String description) {
        this.name = name;
        this.icon = icon;
        this.description = description;
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
}