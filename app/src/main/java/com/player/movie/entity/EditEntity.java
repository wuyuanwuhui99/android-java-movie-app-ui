package com.player.movie.entity;

public class EditEntity {
    private String title;
    private String field;
    private String value;
    private Boolean require;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

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

    public EditEntity(){}

    public EditEntity(String title, String field, String value, Boolean require) {
        this.title = title;
        this.field = field;
        this.value = value;
        this.require = require;
    }

    public void setField(String key,String value){
//         = value;
    }
}
