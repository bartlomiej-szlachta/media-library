package com.szlachta.medialibrary.model

enum class ItemStatusEnum(val position: Int) {
    PLANNED(0),
    IN_PROGRESS(1),
    FINISHED(2),
    ;

    companion object {
        const val TAG = "item-status"

        fun getByPosition(position: Int): ItemStatusEnum {
            return values()[position]
        }
    }
}