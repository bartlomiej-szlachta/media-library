package com.szlachta.medialibrary.ui

enum class ItemTypeEnum(val position: Int, val key: String) {
    GAMES(0, "games"),
    MOVIES(1, "movies"),
    BOOKS(2, "books"),
    ;

    companion object {
        fun getByPosition(position: Int): ItemTypeEnum {
            return values()[position]
        }
    }
}