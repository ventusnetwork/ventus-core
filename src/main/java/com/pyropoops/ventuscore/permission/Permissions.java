package com.pyropoops.ventuscore.permission;

public enum Permissions {
    GET_ITEM("ventuscore.getitem"),
    DISCORD("ventuscore.discord"),
    MAIN_MENU("ventuscore.mainmenu"),
    CHAT_BYPASS("ventuscore.chat.bypass")
    ;
    private String value;

    Permissions(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}