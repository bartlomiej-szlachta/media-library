package com.szlachta.medialibrary.model

enum class ItemTypeEnum(val position: Int) {
    GAMES(0),
    MOVIES(1),
    TV_SHOWS(2),
    BOOKS(3),
    ;

    companion object {
        const val TAG = "item-type"

        fun getByPosition(position: Int): ItemTypeEnum {
            return values()[position]
        }
    }
}