package com.player.movie.entity;

public class EditEntity {
    private String title;
    private String value;
    private Boolean require;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getRequire() {
        return require;
    }

    public void setRequire(Boolean require) {
        this.require = require;
    }

    public EditEntity(String title, String value, Boolean require) {
        this.title = title;
        this.value = value;
        this.require = require;
    }
}
