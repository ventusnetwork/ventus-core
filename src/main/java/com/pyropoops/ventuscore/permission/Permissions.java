package com.pyropoops.ventuscore.permission;

public enum Permissions {
    GET_ITEM("ventuscore.getitem"),
    DISCORD("ventuscore.discord"),
    MAIN_MENU("ventuscore.mainmenu"),
    CHAT_BYPASS("ventuscore.chat.bypass"),
    CHAT_COLOR_PREFIX("ventuscore.chat.color."),
    RAINBOW_CHAT("ventuscore.chat.rainbow"),
    RELOAD("ventuscore.reload")
    ;
    private String value;

    Permissions(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}