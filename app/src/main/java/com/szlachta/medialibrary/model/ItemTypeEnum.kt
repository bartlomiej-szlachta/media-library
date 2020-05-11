package com.szlachta.medialibrary.model

enum class ItemTypeEnum(val position: Int) {
    GAMES(0),
    MOVIES(1),
    BOOKS(2),
    ;

    companion object {
        const val TAG = "item-type"

        fun getByPosition(position: Int): ItemTypeEnum {
            return values()[position]
        }
    }
}