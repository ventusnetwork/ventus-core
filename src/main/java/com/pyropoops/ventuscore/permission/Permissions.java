package com.pyropoops.ventuscore.permission;

public enum Permissions {
    GET_ITEM("ventuscore.getitem")
    ;
    private String value;

    Permissions(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
